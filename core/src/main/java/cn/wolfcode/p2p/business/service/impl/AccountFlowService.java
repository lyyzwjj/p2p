package cn.wolfcode.p2p.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import cn.wolfcode.p2p.business.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.business.mapper.AccountFlowMapper;
import cn.wolfcode.p2p.business.service.IAccountFlowService;

@Service
public class AccountFlowService implements IAccountFlowService {
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public void createTransferFlow(Account account, CreditTransfer ct) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(ct.getBidRequestTitle()+"转让成功"+"可用资金增加"+ct.getBidRequestAmount()+"元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_TRANSFER_SUCCESS);
        flow.setAmount(ct.getBidRequestAmount());
        accountFlowMapper.insert(flow);
    }
    @Override
    public void createSubscribeFlow(Account account, CreditTransfer ct) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(ct.getBidRequestTitle()+"认购成功"+"可用资金减少"+ct.getBidRequestAmount()+"元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_SUBSCRIBE_SUCCESS);
        flow.setAmount(ct.getBidRequestAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void createInterestFeeFlow(Account account, PaymentScheduleDetail psd, BigDecimal interestFee) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote("收取" + userInfoService.get(psd.getInvestorId()).getRealName() + "的XXX标的第" + psd.getMonthIndex() + "次利息手续费,可用资金减少" + interestFee + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_INTEREST_SHARE);
        flow.setAmount(interestFee);
        accountFlowMapper.insert(flow);

    }

    @Override
    public void createReceiveMoneyFlow(Account account, PaymentScheduleDetail psd) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(userInfoService.get(psd.getInvestorId()).getRealName() + "的XXX标的第" + psd.getMonthIndex() + "次收款成功,可用资金增加" + psd.getTotalAmount() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY);
        flow.setAmount(psd.getTotalAmount());
        accountFlowMapper.insert(flow);

    }

    @Override
    public void returnMoneySuccessFlow(Account account, PaymentSchedule ps) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(ps.getBorrowUser().getUsername() + "的" + ps.getBidRequestTitle() + "标" + "的第" + ps.getMonthIndex() + "次还款成功,可用金额减少" + ps.getTotalAmount() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_RETURN_MONEY);
        flow.setAmount(ps.getTotalAmount());
        accountFlowMapper.insert(flow);

    }

    @Override
    public void createMoneyWithdrawFlowFailedFlow(Account account, MoneyWithdraw mw) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(mw.getAccountName() + "提现失败,冻结金额减少" + mw.getMoneyAmount() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_WITHDRAW_UNFREEZED);
        flow.setAmount(mw.getMoneyAmount());
        accountFlowMapper.insert(flow);

    }

    @Override
    public void createMoneyWithdrawFeeFlow(Account account, MoneyWithdraw mw) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(mw.getAccountName() + "提现成功,提现手续费收取" + mw.getFee() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
        flow.setAmount(mw.getFee());
        accountFlowMapper.insert(flow);

    }

    @Override
    public void createMoneyWithdrawFlowSuccessFlow(Account account, MoneyWithdraw mw) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(mw.getAccountName() + "提现成功,冻结金额减少" + mw.getMoneyAmount() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_WITHDRAW);
        flow.setAmount(mw.getMoneyAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void createMoneyWithdrawFlow(Account account, MoneyWithdraw mw) {
        AccountFlow flow = this.createBaseFlow(account);
        flow.setNote(mw.getAccountName() + "申请提现" + mw.getMoneyAmount() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_WITHDRAW_FREEZED);
        flow.setAmount(mw.getMoneyAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void creatRechargeOfflineFlow(Account account, RechargeOffline ro) {
        AccountFlow flow = new AccountFlow();
        flow.setAccountId(account.getId());
        flow.setActionTime(new Date());
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE);
        flow.setAmount(ro.getAmount());
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setNote("线下充值成功，充值金额为：" + ro.getAmount());
        flow.setUsableAmount(account.getUsableAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void createBidFlow(Account account, BidRequest br) {
        AccountFlow flow = createBaseFlow(account);
        flow.setNote("投" + br.getTitle() + "标:" + br.getBidRequestAmount() + "元,投标成功");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL);
        flow.setAmount(br.getBidRequestAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void creatBidFailedFlow(Account account, Bid bid) {
        AccountFlow flow = createBaseFlow(account);
        flow.setNote("发标终审失败,投资人" + bid.getBidUser().getUsername() + "退款" + bid.getAvailableAmount() + "成功");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);
        flow.setAmount(bid.getAvailableAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void createBorrowSuccessFlow(Account account, BidRequest br) {
        AccountFlow flow = createBaseFlow(account);
        flow.setNote(br.getTitle() + "标发表审核成功,发标人" + br.getCreateUser().getUsername() + "获得" + br.getBidRequestAmount()
                + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);
        flow.setAmount(br.getBidRequestAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void createManageFeeFlow(Account account, BidRequest br, BigDecimal manageFee) {
        AccountFlow flow = createBaseFlow(account);
        flow.setNote(br.getCreateUser().getUsername() + "账户扣除" + manageFee + "元平台管理费");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_CHARGE);
        flow.setAmount(br.getBidRequestAmount());
        accountFlowMapper.insert(flow);
    }

    @Override
    public void createBidSuccessFlow(Account account, Bid bid, BidRequest br) {
        AccountFlow flow = createBaseFlow(account);
        flow.setNote(br.getTitle() + "标发表审核成功,投标人" + bid.getBidUser().getUsername() + "冻结金额减少"
                + bid.getAvailableAmount() + "元");
        flow.setActionType(Constants.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);
        flow.setAmount(bid.getAvailableAmount());
        accountFlowMapper.insert(flow);
    }

    private AccountFlow createBaseFlow(Account account) {
        AccountFlow flow = new AccountFlow();
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setUsableAmount(account.getUsableAmount());
        flow.setActionTime(new Date());
        flow.setAccountId(account.getId());
        return flow;
    }

}
