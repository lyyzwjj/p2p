package cn.wolfcode.p2p.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.business.domain.MoneyWithdraw;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.domain.SystemAccountFlow;
import cn.wolfcode.p2p.business.mapper.SystemAccountFlowMapper;
import cn.wolfcode.p2p.business.service.ISystemAccountFlowService;

/**
 * Created by WangZhe on 2018/7/31.
 */
@Service
@Transactional
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService {
    @Autowired
    private SystemAccountFlowMapper flowMapper;
    @Override
    public void save(SystemAccountFlow record) {
    	flowMapper.insert(record);
    }
    
    @Override
	public void createMoneyWithdrawFeeFlow(SystemAccount account, MoneyWithdraw mw) {
    	 SystemAccountFlow flow = new SystemAccountFlow();
         flow.setUsableAmount(account.getUsableAmount());
         flow.setFreezeAmount(account.getFreezedAmount());
         flow.setAmount(mw.getFee());
         flow.setActionTime(new Date());
         flow.setActionType(Constants.SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
         flow.setNote("平台收取提现手续费" + mw.getFee() + "元");
         this.flowMapper.insert(flow);
		
	}


    @Override
    public void chargeManageFeeFlow(SystemAccount systemAccount, BigDecimal manageFee) {
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setUsableAmount(systemAccount.getUsableAmount());
        flow.setFreezeAmount(systemAccount.getFreezedAmount());
        flow.setAmount(manageFee);
        flow.setActionTime(new Date());
        flow.setActionType(Constants.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        flow.setNote("平台收取借款管理费" + manageFee + "元");
        this.flowMapper.insert(flow);
    }

	@Override
	public void chargeInterestFeeFlow(SystemAccount systemAccount, BigDecimal interestFee) {
		SystemAccountFlow flow = new SystemAccountFlow();
        flow.setUsableAmount(systemAccount.getUsableAmount());
        flow.setFreezeAmount(systemAccount.getFreezedAmount());
        flow.setAmount(interestFee);
        flow.setActionTime(new Date());
        flow.setActionType(Constants.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        flow.setNote("平台收取借款管理费" + interestFee + "元");
        this.flowMapper.insert(flow);
		
	}

}
