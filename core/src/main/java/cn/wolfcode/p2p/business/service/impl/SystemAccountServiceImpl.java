package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.mapper.SystemAccountMapper;
import cn.wolfcode.p2p.business.service.ISystemAccountFlowService;
import cn.wolfcode.p2p.business.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by WangZhe on 2018/7/31.
 */
@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService{
    @Autowired
    private SystemAccountMapper systemAccountMapper;
    @Autowired
    private ISystemAccountFlowService safService;
    @Override
    public SystemAccount selectCurrent() {
        return systemAccountMapper.selectCurrent();
    }
    
    @Override
    public void update(SystemAccount record) {
        int count = systemAccountMapper.updateByPrimaryKey(record);
        if (count == 0) {
            throw new DisplayableException("系统繁忙,请重试");
        }
    }
    
    @Override
	public void chargeInterestFee(PaymentScheduleDetail psd, BigDecimal interestFee) {
    	 SystemAccount systemAccount = systemAccountMapper.selectCurrent();
         systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interestFee));
         this.update(systemAccount);
         safService.chargeInterestFeeFlow(systemAccount,interestFee);
		
	}

	@Override
    public void chargeManageFee(BidRequest br, BigDecimal manageFee) {
        SystemAccount systemAccount = systemAccountMapper.selectCurrent();
        systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(manageFee));
        this.update(systemAccount);
        safService.chargeManageFeeFlow(systemAccount,manageFee);
    }
}
