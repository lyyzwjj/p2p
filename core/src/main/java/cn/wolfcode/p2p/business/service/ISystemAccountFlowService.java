package cn.wolfcode.p2p.business.service;

import java.math.BigDecimal;

import cn.wolfcode.p2p.business.domain.MoneyWithdraw;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.domain.SystemAccountFlow;

/**
 * Created by WangZhe on 2018/7/31.
 */
public interface ISystemAccountFlowService {
    void save(SystemAccountFlow record);


    /**
     * 平台收取管理费流水
     *
     * @param systemAccount
     * @param manageFee
     */
    void chargeManageFeeFlow(SystemAccount systemAccount, BigDecimal manageFee);

    /**
     * 平台收取提现手续费
     * 
     * @param account
     * @param mw
     */
	void createMoneyWithdrawFeeFlow(SystemAccount account, MoneyWithdraw mw);

	/**
	 * 平台收取利息手续费
	 * 
	 * @param systemAccount
	 * @param interestFee
	 */
	void chargeInterestFeeFlow(SystemAccount systemAccount, BigDecimal interestFee);
}
