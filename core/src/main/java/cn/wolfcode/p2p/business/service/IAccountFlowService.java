package cn.wolfcode.p2p.business.service;

import java.math.BigDecimal;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.business.domain.*;

public interface IAccountFlowService {
    /**
     * 账户充值流水
     *
     * @param account
     * @param ro
     */
	void creatRechargeOfflineFlow(Account account, RechargeOffline ro);

    /**
     * 投资人投标流水
     *
     * @param account
     * @param br
     */
    void createBidFlow(Account account, BidRequest br);

    /**
     * 借款失败投资人退款流水
     *
     * @param account
     * @param bid
     */
    void creatBidFailedFlow(Account account, Bid bid);

    /**
     * 借款成功用户借款成功流水
     *
     * @param account
     * @param br
     */
    void createBorrowSuccessFlow(Account account, BidRequest br);

    /**
     * 借款手续费支出流水
     *
     * @param account
     * @param manageFee
     */
    void createManageFeeFlow(Account account,BidRequest br, BigDecimal manageFee);

    /**
     * 投资人投资成功流水
     *
     * @param account
     * @param bid
     */
    void createBidSuccessFlow(Account account,Bid bid,BidRequest br);
    /**
     * 提现申请流水
     * 
     * @param account
     * @param mw
     */
	void createMoneyWithdrawFlow(Account account, MoneyWithdraw mw);
	/**
	 * 提现成功流水
	 * 
	 * @param account
	 * @param mw
	 */
	void createMoneyWithdrawFlowSuccessFlow(Account account, MoneyWithdraw mw);

	/**
	 * 提现成功系统账户收取手续费流水
	 * 
	 * @param account
	 * @param mw
	 */
	void createMoneyWithdrawFeeFlow(Account account, MoneyWithdraw mw);
	/**
	 * 提现失败系统账户收取手续费流水
	 * 
	 * @param account
	 * @param mw
	 */
	void createMoneyWithdrawFlowFailedFlow(Account account, MoneyWithdraw mw);
	/**
	 * 还款成功账户可用资金减少流水
	 * 
	 * @param account
	 * @param ps
	 */
	void returnMoneySuccessFlow(Account account, PaymentSchedule ps);
	/**
	 * 投资人收款成功 可用资金增加流水
	 * 
	 * @param account
	 * @param psd
	 */
	void createReceiveMoneyFlow(Account account, PaymentScheduleDetail psd);
	/**
	 * 投资人支付利息手续费 可用资金减少流水
	 * 
	 * @param account
	 * @param psd
	 * @param interestFee
	 */
	void createInterestFeeFlow(Account account, PaymentScheduleDetail psd, BigDecimal interestFee);

	/**
	 * 认购债权转让标成功 认购人可用资金减少流水
	 *
	 * @param account
	 * @param ct
	 */
    void createSubscribeFlow(Account account, CreditTransfer ct);
	/**
	 * 转让债权转让标成功 转让人可用资金增加流水
	 *
	 * @param account
	 * @param ct
	 */
    void createTransferFlow(Account account, CreditTransfer ct);
}
