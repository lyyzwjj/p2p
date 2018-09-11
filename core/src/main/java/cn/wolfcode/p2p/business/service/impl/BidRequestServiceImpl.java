package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.*;
import cn.wolfcode.p2p.business.domain.*;
import cn.wolfcode.p2p.business.mapper.BidMapper;
import cn.wolfcode.p2p.business.mapper.BidRequestMapper;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleMapper;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.business.service.*;
import cn.wolfcode.p2p.business.util.CalculatetUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Service
@Transactional
public class BidRequestServiceImpl implements IBidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IBidRequestAuditHistoryService bhService;
    @Autowired
    private IAccountFlowService flowService;
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private ISystemAccountService saService;
    @Autowired
    private IPaymentScheduleService psService;
    @Autowired
    private IPaymentScheduleDetailService psdService;
    @Autowired
    private PaymentScheduleMapper psMapper;
    @Autowired
    private ICreditTransferService transferService;

    @Override
    public void save(BidRequest bidRequest) {
        bidRequestMapper.insert(bidRequest);
    }

    @Override
    public void update(BidRequest bidRequest) {
        int count = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (count == 0) {
            System.out.println("乐观锁异常,BidRequest:" + bidRequest.getId());
            throw new DisplayableException("系统繁忙,请重试");
        }
    }

    @Override
    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public void bid(BigDecimal amount, Long bidRequestId) {
        // 检查
        // 投标 >= 最小投标金额
        // 投标金额<=用户可用余额,剩余借款金额
        // 借款处于招标状态
        // 当前用户是正常投资人非借款人
        // 没有超过招标时间
        // 投标
        // 创建投标对象,设置属性
        // 借款对象:增加投标次数,增加投标金额
        // 投资人 账户可用余额减少 冻结金额增加 创建投标流水
        // 如果标投标满了
        // 修改招标状态为风控终审
        // 批量修改投标状态
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(bidRequestId);
        Account borrowAccount = accountService.get(bidRequest.getCreateUser().getId());
        if (bidRequest != null && bidRequest.getBidRequestState() == Constants.BIDREQUEST_STATE_BIDDING
                && amount.compareTo(Constants.BID_AMOUNT_MIN) >= 0
                && amount.compareTo(borrowAccount.getRemainBorrowLimit()) <= 0
                && amount.compareTo(bidRequest.getRemainBidAmount()) <= 0
                && !bidRequest.getCreateUser().getId().equals(UserContext.getCurrent().getId())
                && !new Date().after(bidRequest.getDisableDate())) {
            Bid bid = new Bid();
            bid.setActualRate(bidRequest.getCurrentRate());
            bid.setAvailableAmount(amount);
            bid.setBidRequestId(bidRequestId);
            bid.setBidRequestState(bidRequest.getBidRequestState());
            bid.setBidRequestTitle(bidRequest.getTitle());
            bid.setBidTime(new Date());
            bid.setBidUser(UserContext.getCurrent());
            bidRequest.setBidCount(bidRequest.getBidCount() + 1);
            bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));
            Account bidAccount = accountService.get(UserContext.getCurrent().getId());
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(amount));
            bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().add(amount));
            flowService.createBidFlow(bidAccount, bidRequest);
            bidMapper.insert(bid);
            if (bidRequest.getCurrentSum().equals(bidRequest.getBidRequestAmount())) {
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
                bidMapper.updateStates(bidRequest.getId(), Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
            }
            accountService.update(bidAccount);
            update(bidRequest);
        }
    }

    @Override
    public void fullAudit1(Long id, String remark, int state) {
        // fullAudit1
        // 判断,借款处于满标一审状态
        // 创建借款审核对象,设置对应参数 BidRequestAuditHistory
        // 如果审核通过
        // 修改借款状态为财务终审
        // 修改投标对象状态(批量)
        // 如果审核拒绝
        // 修改借款对象为满审核拒绝
        // 修改投标对象状态
        // 遍历投标对象 遍历bids
        // 投标人账户可用余额增加,冻结金额减少
        // 增加投标失败流水
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        AssertUtil.isNotNull(br, "参数异常");
        AssertUtil.isTrue(br.getBidRequestState() == Constants.BIDREQUEST_STATE_APPROVE_PENDING_1, "状态异常");
        this.createBidRequestAuditHistory(br, remark, state, BidRequestAuditHistory.AUDIT_PUBLISH);
        if (state == BidRequestAuditHistory.STATE_PASS) {
            br.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
            bidMapper.updateStates(br.getId(), Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
        } else {
            this.fullAuditFailed(br);
            this.changeUserInfoBitState(br);
        }
        this.update(br);
    }

    @Override
    public void fullAudit2(Long id, String remark, int state) {
        // 审核通过
        // 1.对于借款对象本身
        // 1.1修改借款状态,还款中
        // 1.2修改投标的状态
        // 2.对于借款人
        // 2.1借款成功,可用余额增加
        // 2.2生成借款成功流水
        // 2.3代还金额增加
        // 2.4剩余信用额度减少
        // 2.5去掉状态码
        // 2.6支付借款手续费,可用余额减少
        // 2.7生成支付流水
        //
        // 2.8系统账户收取借款手续费并生成流水
        //
        // 3.对于投资人
        // 3.1遍历投标
        // 3.2投标成功,冻结金额减少
        // 3.3生成投标成功流水
        // 3.4代收本金/代收利息增加
        // 4.对于后期业务
        // 4.1声称该借款的还款计划对象及对应的还款明细对象
        // 审核拒绝
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        AssertUtil.isNotNull(br, "参数异常");
        AssertUtil.isTrue(br.getBidRequestState() == Constants.BIDREQUEST_STATE_APPROVE_PENDING_2, "状态异常");
        this.createBidRequestAuditHistory(br, remark, state, BidRequestAuditHistory.AUDIT_PUBLISH);
        if (state == BidRequestAuditHistory.STATE_PASS) {
            br.setBidRequestState(Constants.BIDREQUEST_STATE_PAYING_BACK);
            this.bidMapper.updateStates(br.getId(), Constants.BIDREQUEST_STATE_PAYING_BACK);
            // 2.对于借款人
            // 2.1借款成功,可用余额增加
            Account borrowAccount = accountService.get(br.getCreateUser().getId());
            borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().add(br.getBidRequestAmount()));
            // 2.2生成借款成功流水
            this.flowService.createBorrowSuccessFlow(borrowAccount, br);
            // 2.3待还金额增加
            borrowAccount.setUnReturnAmount(
                    borrowAccount.getUnReturnAmount().add(br.getBidRequestAmount()).add(br.getTotalRewardAmount()));
            // 2.4剩余信用额度减少
            borrowAccount.setRemainBorrowLimit(borrowAccount.getRemainBorrowLimit().subtract(br.getBidRequestAmount()));
            // 2.5去掉状态码
            // 2.6支付借款手续费,可用余额减少
            BigDecimal manageFee = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
            borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(manageFee));
            // 2.7生成支付流水
            this.flowService.createManageFeeFlow(borrowAccount, br, manageFee);
            // 2.8系统账户收取借款手续费并生成流水
            this.saService.chargeManageFee(br, manageFee);
            // 3.对于投资人
            List<Bid> bids = this.bidMapper.listByBidRequestId(br.getId());
            Map<Long, Account> bidAccounts = new HashMap<>();
            // 3.1遍历投标
            for (Bid bid : bids) {
                Long key = bid.getBidUser().getId();
                Account bidAccount = bidAccounts.get(key);
                if (bidAccount == null) {
                    bidAccount = this.accountService.get(key);
                    bidAccounts.put(key, bidAccount);
                }
                // 3.2投标成功,冻结金额减少
                bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
                // 3.3生成投标成功流水
                this.flowService.createBidSuccessFlow(bidAccount, bid, br);
            }
            // 4.对于后期业务
            // 4.1声称该借款的还款计划对象及对应的还款明细对象
            List<PaymentSchedule> pss = this.createPaymentSchedules(br);

            // 3.4待收本金/待收利息增加
            for (PaymentSchedule ps : pss) {
                List<PaymentScheduleDetail> details = ps.getDetails();
                for (PaymentScheduleDetail detail : details) {
                    Account bidAccount = bidAccounts.get(detail.getInvestorId());
                    bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().add(detail.getInterest()));
                    bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().add(detail.getPrincipal()));
                }
            }
            for (Account bidAccount : bidAccounts.values()) {
                accountService.update(bidAccount);
            }
            accountService.update(borrowAccount);
        } else {
            fullAuditFailed(br);
        }
        this.changeUserInfoBitState(br);
        this.update(br);

    }

    @Override
    public void apply(BidRequest to) {
        UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
        Account account = accountService.get(UserContext.getCurrent().getId());
        if (userInfo.getHasBasicInfo() && userInfo.getHasRealAuth() && userInfo.getHasVideoAuth() // 是否能借款
                && !userInfo.getHasBidRequestInProcess() // 是否有借款在审核流程当中
                && to.getBidRequestAmount().compareTo(account.getRemainBorrowLimit()) <= 0 // 系统最小借款金额<=借款金额<=剩余可用额度
                && to.getBidRequestAmount().compareTo(Constants.BORROW_AMOUNT_MIN) >= 0
                && to.getCurrentRate().compareTo(new BigDecimal("20.0000")) <= 0 // 5<=借款利息<=20
                && to.getCurrentRate().compareTo(new BigDecimal("5.0000")) >= 0
                && to.getMinBidAmount().compareTo(Constants.BID_AMOUNT_MIN) >= 0 // 最小投标金额 >= 系统最小投标金额
                ) {
            // 更新数据
            BidRequest br = new BidRequest();
            br.setApplyTime(new Date());
            br.setBidRequestAmount(to.getBidRequestAmount());
            br.setBidRequestState(Constants.BIDREQUEST_TYPE_NORMAL);
            br.setBidRequestType(Constants.BIDREQUEST_STATE_APPLY);
            br.setCreateUser(UserContext.getCurrent());
            br.setCurrentRate(to.getCurrentRate());
            br.setDescription(to.getDescription());
            br.setDisableDays(to.getDisableDays());
            br.setMinBidAmount(to.getMinBidAmount());
            br.setMonthes2Return(to.getMonthes2Return());
            br.setReturnType(to.getReturnType());
            br.setTitle(to.getTitle());
            br.setTotalRewardAmount(CalculatetUtil.calTotalInterest(br.getReturnType(), br.getBidRequestAmount(),
                    br.getCurrentRate(), br.getMonthes2Return()));
            bidRequestMapper.insert(br);
            // 借款人添加状态码
            userInfo.addState(BitStateUtil.HAS_BIDREQUEST_PROCESS);
            userInfoService.update(userInfo);
        }
    }

    @Override
    public PageInfo<BidRequest> queryInvestList(BidRequestQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<BidRequest> list = bidRequestMapper.queryDate(qo);
        return new PageInfo<>(list);
    }

    @Override
    public void publishAudit(Long id, String remark, int state, Date publishTime) {
        // 判断,借款处于发表前的审核状态
        // 创建一个借款审核对象,设置相关是的属性值;
        // 如果审核通过
        // 修改借款状态
        // 设置借款发表时间/到期时间/风控意见
        // 如果审核失败
        // 修改借款状态
        // 去掉页用户相关状态
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        AssertUtil.isNotNull(br, "参数异常");
        AssertUtil.isTrue(br.getBidRequestState() == Constants.BIDREQUEST_STATE_APPLY, "状态异常");
        this.createBidRequestAuditHistory(br, remark, state, BidRequestAuditHistory.AUDIT_PUBLISH);
        if (state == BidRequestAuditHistory.STATE_PASS) {
            if (publishTime != null) {
                br.setPublishTime(publishTime);
                br.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
            } else {
                br.setPublishTime(new Date());
                br.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
            }
            br.setDisableDate(DateUtil.addDays(br.getPublishTime(), br.getDisableDays()));
            br.setNote(remark);
        } else {
            br.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_REFUSE);
            this.changeUserInfoBitState(br);
        }
        bidRequestMapper.updateByPrimaryKey(br);
    }

    /**
     * 保存发表审核纪录
     *
     * @param br
     * @param remark
     * @param state
     * @param auditType
     */
    private void createBidRequestAuditHistory(BidRequest br, String remark, int state, int auditType) {
        BidRequestAuditHistory bh = new BidRequestAuditHistory();
        bh.setBidRequestId(br.getId());
        bh.setApplier(br.getCreateUser());
        bh.setApplyTime(br.getApplyTime());
        bh.setAuditor(UserContext.getCurrent());
        bh.setAuditTime(new Date());
        bh.setAuditType(auditType);
        bh.setRemark(remark);
        bh.setState(state);
        bhService.save(bh);
    }

    /**
     * 发标为终止状态时改变用户状态
     *
     * @param br
     */
    private void changeUserInfoBitState(BidRequest br) {
        UserInfo createUser = userInfoService.get(br.getCreateUser().getId());
        createUser.removeState(BitStateUtil.HAS_BIDREQUEST_PROCESS);
        userInfoService.update(createUser);
    }

    /**
     * 风控终审和财务终审审核失败操作
     *
     * @param br
     */
    private void fullAuditFailed(BidRequest br) {
        br.setBidRequestState(Constants.BIDREQUEST_STATE_REJECTED);
        List<Bid> bids = bidMapper.listByBidRequestId(br.getId());
        for (Bid bid : bids) {
            bid.setBidRequestState(Constants.BIDREQUEST_STATE_REJECTED);
            Account bidAccount = accountService.get(bid.getBidUser().getId());
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(bid.getAvailableAmount()));
            bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
            flowService.creatBidFailedFlow(bidAccount, bid);
            bidMapper.updateByPrimaryKey(bid);
            accountService.update(bidAccount);
        }

    }

    /**
     * 生成还款明细
     *
     * @param br
     * @return
     */
    private List<PaymentSchedule> createPaymentSchedules(BidRequest br) {
        List<PaymentSchedule> pss = new ArrayList<>();
        PaymentSchedule ps;
        BigDecimal totalPricipal = Constants.ZERO;
        BigDecimal totalInterest = Constants.ZERO;
        for (int i = 1; i < br.getMonthes2Return() + 1; i++) {
            ps = new PaymentSchedule();
            ps.setBidRequestId(br.getId());
            ps.setBidRequestTitle(br.getTitle());
            ps.setBidRequestType(br.getBidRequestType());
            ps.setBorrowUser(br.getCreateUser());
            ps.setDeadLine(DateUtil.addMonths(br.getPublishTime(), i));
            ps.setMonthIndex(i);
            ps.setReturnType(br.getReturnType());
            ps.setState(Constants.PAYMENT_STATE_NORMAL);
            if (i < br.getMonthes2Return()) {
                BigDecimal totalAmount = CalculatetUtil.calMonthToReturnMoney(br.getReturnType(),
                        br.getBidRequestAmount(), br.getCurrentRate(), i, br.getMonthes2Return());
                BigDecimal interest = CalculatetUtil.calMonthlyInterest(br.getReturnType(), br.getBidRequestAmount(),
                        br.getCurrentRate(), i, br.getMonthes2Return());
                BigDecimal principal = totalAmount.subtract(interest);
                totalPricipal = totalPricipal.add(principal);
                totalInterest = totalInterest.add(interest);
                ps.setInterest(interest);
                ps.setPrincipal(principal);
                ps.setTotalAmount(totalAmount);
            } else {
                ps.setInterest(br.getTotalRewardAmount().subtract(totalInterest));
                ps.setPrincipal(br.getBidRequestAmount().subtract(totalPricipal));
                ps.setTotalAmount(ps.getInterest().add(ps.getPrincipal()));
            }
            psService.save(ps);
            this.createPaymentScheduleDetails(ps, br);
            pss.add(ps);
        }
        return pss;
    }

    /**
     * 生成还款计划明细
     *
     * @param ps
     * @param br
     */
    private void createPaymentScheduleDetails(PaymentSchedule ps, BidRequest br) {
        PaymentScheduleDetail psd;
        List<Bid> bids = br.getBids();
        BigDecimal totalInterest = Constants.ZERO;
        BigDecimal totalPricipal = Constants.ZERO;
        for (int i = 1; i < br.getBidCount() + 1; i++) {
            psd = new PaymentScheduleDetail();
            psd.setMonthIndex(ps.getMonthIndex());
            psd.setPayDate(ps.getPayDate());
            psd.setPaymentScheduleId(ps.getId());
            psd.setBidRequestId(br.getId());
            psd.setDeadLine(ps.getDeadLine());
            psd.setReturnType(ps.getReturnType());
            Bid bid = bids.get(i - 1);
            psd.setBidId(bid.getId());
            psd.setBidAmount(bid.getAvailableAmount());
            psd.setBorrowUser(br.getCreateUser());
            psd.setInvestorId(bid.getBidUser().getId());
            BigDecimal rate = bid.getAvailableAmount().divide(br.getBidRequestAmount(), Constants.SCALE_CAL,
                    BigDecimal.ROUND_HALF_UP);
            if (i < br.getBidCount()) {
                psd.setInterest(
                        rate.multiply(ps.getInterest()).setScale(Constants.SCALE_STORE, BigDecimal.ROUND_HALF_UP));
                totalInterest = totalInterest.add(psd.getInterest());
                psd.setPrincipal(
                        rate.multiply(ps.getPrincipal()).setScale(Constants.SCALE_STORE, BigDecimal.ROUND_HALF_UP));
                totalPricipal = totalPricipal.add(psd.getPrincipal());
                psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));
            } else {
                psd.setInterest(ps.getInterest().subtract(totalInterest));
                psd.setPrincipal(ps.getPrincipal().subtract(totalPricipal));
                psd.setTotalAmount(psd.getInterest().add(psd.getPrincipal()));
            }
            psdService.save(psd);
            ps.getDetails().add(psd);
        }
    }

    @Override
    public void returnMoney(Long id) {
        // 1.获取还款对象
        PaymentSchedule ps = psService.get(id);
        Account borrowAccount = accountService.get(UserContext.getCurrent().getId());

        // 2.判断条件是否满足:
        // 2.1还款对象状态是待还
        // 2.2判断当前用户是还款人
        // 2.3账户金额>=还款金额
        if (ps.getState() == Constants.PAYMENT_STATE_NORMAL && ps.getBorrowUser().getId().equals(UserContext.getCurrent().getId())
                && borrowAccount.getUsableAmount().compareTo(ps.getTotalAmount()) >= 0) {
            // 3对于还款对象和还款明细对象操作
            // 3.1设置还款的时间和状态
            // 3.2还款明细批量 设置还款时间
            ps.setPayDate(new Date());
            ps.setState(Constants.PAYMENT_STATE_DONE);
            // 4.对于还款人
            // 4.1可用金额减少,生成还款成功流水
            borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(ps.getTotalAmount()));
            flowService.returnMoneySuccessFlow(borrowAccount, ps);
            // 4.2待还金额减少
            borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount().subtract(ps.getTotalAmount()));
            // 4.3剩余授信额度金额增加
            borrowAccount.setRemainBorrowLimit(borrowAccount.getRemainBorrowLimit().add(ps.getPrincipal()));
            accountService.update(borrowAccount);
            psService.update(ps);
            // 5.针对投资人
            List<PaymentScheduleDetail> details = ps.getDetails();
            Map<Long, Account> bidAccounts = new HashMap<>();
            List<CreditTransfer> needCancels = new ArrayList<>();
            // 5.1遍历还款明细
            for (PaymentScheduleDetail psd : details) {
                Long key = psd.getInvestorId();
                Account bidAccount = bidAccounts.get(key);
                if (bidAccount == null) {
                    bidAccount = this.accountService.get(key);
                    bidAccounts.put(key, bidAccount);
                }
                // 5.2投资人可用余额增加,生成收款流水
                bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(psd.getTotalAmount()));
                flowService.createReceiveMoneyFlow(bidAccount, psd);
                BigDecimal interestFee = CalculatetUtil.calInterestManagerCharge(psd.getInterest());
                // 5.3支付平台利息管理费,生成支付利息流水.
                bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(interestFee));
                flowService.createInterestFeeFlow(bidAccount, psd, interestFee);
                // 5.4平台收取利息管理费,生成收取利息流水
                saService.chargeInterestFee(psd, interestFee);
                // 5.5待收利息和待收本金减少
                bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().subtract(psd.getInterest()));
                bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().subtract(psd.getPrincipal()));
                //处理债权转让问题
                //如果此还款明细处于债权转让中,则找到对应的债权标
                if (psd.getIntrans() == Constants.IN_TRANS) {
                    CreditTransfer needCancel = transferService.findNeedCancel(psd.getBidId());
                    needCancels.add(needCancel);
                }
            }
            for (CreditTransfer needCancel : needCancels) {
                transferService.cancel(needCancel);
            }
            psdService.batchUpdatePayDate(new Date(), id);
            for (Account bidAccount : bidAccounts.values()) {
                accountService.update(bidAccount);
            }
            // 6.其他
            // 如果是最后一个还款,修改还款状态,
            PaymentScheduleQueryObject qo = new PaymentScheduleQueryObject();
            qo.setBidRequestId(ps.getBidRequestId());
            List<PaymentSchedule> pss = this.psMapper.queryData(qo);
            boolean flag = true;
            for (PaymentSchedule temp : pss) {
                if (temp.getState() != Constants.PAYMENT_STATE_DONE) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                BidRequest bidRequest = this.get(ps.getBidRequestId());
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
                // 批量修改投标对象状态
                bidMapper.updateStates(bidRequest.getId(), Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
                this.update(bidRequest);
            }
        }

    }

    @Override
    public void publishInTime() {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
        qo.setPublishTime(new Date());
        List<BidRequest> bidRequests = this.bidRequestMapper.queryDate(qo);
        for (BidRequest bidRequest : bidRequests) {
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
            this.update(bidRequest);
        }
    }

}
