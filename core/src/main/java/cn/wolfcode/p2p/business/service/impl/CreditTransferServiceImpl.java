package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.CreditTransfer;
import cn.wolfcode.p2p.business.mapper.CreditTransferMapper;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleDetailMapper;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.business.service.ICreditTransferService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangZhe on 2018/8/3.
 */
@Service
@Transactional
public class CreditTransferServiceImpl implements ICreditTransferService {
    @Autowired
    private CreditTransferMapper creditTransferMapper;
    @Autowired
    private PaymentScheduleDetailMapper psdMapper;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService flowService;

    @Override
    public void update(CreditTransfer creditTransfer) {
        int i = creditTransferMapper.updateByPrimaryKey(creditTransfer);
        if (i < 1) {
            throw new DisplayableException("状态码异常,请重新操作");
        }
    }

    @Override
    public PageInfo<CreditTransfer> listCanTransferCredit(CreditTransferQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<CreditTransfer> list = creditTransferMapper.ListCanTransferCredit(qo, UserContext.getCurrent().getId());
        return new PageInfo<>(list);
    }

    @Override
    public void creditTransfer(Long[] bidId) {
        if (bidId != null) {
            List<CreditTransfer> readyForCreates = this.creditTransferMapper.ListReadyTransferCredit(UserContext.getCurrent().getId(), bidId);
            for (CreditTransfer ct : readyForCreates) {
                ct.setTransDate(new Date());
                ct.setTransFrom(UserContext.getCurrent());
                ct.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
                creditTransferMapper.insert(ct);
                this.psdMapper.changePaymentScheduleDetailTransState(ct.getBidId(), Constants.IN_TRANS);
            }
        }
    }

    @Override
    public PageInfo<CreditTransfer> query(CreditTransferQueryObject cto) {
        PageHelper.startPage(cto.getCurrentPage(), cto.getPageSize());
        List<CreditTransfer> list = creditTransferMapper.queryData(cto);
        return new PageInfo<>(list);
    }

    @Override
    public void subscribe(Long id) {
        //认购
        //页面传过去认购债权对象的的id
        CreditTransfer ct = creditTransferMapper.selectByPrimaryKey(id);
        BidRequest oldBr = bidRequestService.get(ct.getBidRequestId());
        Account transToAccount = accountService.get(UserContext.getCurrent().getId());
        Account transFromAccount = accountService.get(ct.getTransFrom().getId());
        //判断招标中
        if (ct.getBidRequestState() == Constants.BIDREQUEST_STATE_BIDDING
                //当前用户不是原借款人,也不是转让人
                && !UserContext.getCurrent().getId().equals(ct.getTransFrom().getId())
                && !UserContext.getCurrent().getId().equals(oldBr.getCreateUser().getId())
                //当前可用余额大于认购本金
                && transToAccount.getUsableAmount().compareTo(ct.getBidRequestAmount()) >= 0) {
            ct.setTransTo(UserContext.getCurrent());
            ct.setBidRequestState(Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            ct.setTransDate(new Date());
            this.update(ct);
            //认购人
            //可用余额减少生成收购流水
            transToAccount.setUsableAmount(transToAccount.getUsableAmount().subtract(ct.getBidRequestAmount()));
            flowService.createSubscribeFlow(transToAccount, ct);
            //代收本金增加 代收利息增加
            transToAccount.setUnReceivePrincipal(transToAccount.getUnReceivePrincipal().add(ct.getBidRequestAmount()));
            transToAccount.setUnReceiveInterest(transToAccount.getUnReceiveInterest().add(ct.getRemainInterest()));
            accountService.update(transToAccount);
            //转让人
            //可用余额增加生成转让流水
            transFromAccount.setUsableAmount(transFromAccount.getUsableAmount().add(ct.getBidRequestAmount()));
            flowService.createTransferFlow(transFromAccount, ct);
            //代收本金减少,代收利息减少
            transFromAccount.setUnReceivePrincipal(transFromAccount.getUnReceivePrincipal().subtract(ct.getBidRequestAmount()));
            transFromAccount.setUnReceiveInterest(transFromAccount.getUnReceiveInterest().subtract(ct.getRemainInterest()));
            accountService.update(transFromAccount);
            //其他
            //还款明细收款人改变
            psdMapper.batchUpdateInvestor(UserContext.getCurrent().getId(), ct.getBidId());
            //还款明细instrans状态改变
            psdMapper.changePaymentScheduleDetailTransState(ct.getBidId(), Constants.NOT_IN_TRANS);
        }else{
            throw new DisplayableException("状态异常请重试");
        }
    }

    @Override
    public CreditTransfer findNeedCancel(Long bidId) {
        return creditTransferMapper.findNeedCancel(bidId);
    }

    @Override
    public void cancel(CreditTransfer needCancel) {
        needCancel.setBidRequestState(Constants.BIDREQUEST_STATE_UNDO);
        this.update(needCancel);
        this.creditTransferMapper.changePaymentScheduleDetailTransState(needCancel.getBidId(),Constants.NOT_IN_TRANS);
    }

}