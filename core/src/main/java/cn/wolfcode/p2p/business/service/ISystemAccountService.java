package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.domain.SystemAccount;

import java.math.BigDecimal;


/**
 * Created by WangZhe on 2018/7/31.
 */
public interface ISystemAccountService {
    SystemAccount selectCurrent();
    void update(SystemAccount record);

    /**
     * 平台管理费收取
     *
     * @param br
     * @param manageFee
     */
    void chargeManageFee(BidRequest br, BigDecimal manageFee);
    /**
     * 平台利息手续费收取
     * 
     * @param psd
     * @param interestFee
     */
	void chargeInterestFee(PaymentScheduleDetail psd, BigDecimal interestFee);
}
