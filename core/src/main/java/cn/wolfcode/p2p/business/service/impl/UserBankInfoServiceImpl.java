package cn.wolfcode.p2p.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.BitStateUtil;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.MoneyWithdraw;
import cn.wolfcode.p2p.business.domain.UserBankInfo;
import cn.wolfcode.p2p.business.mapper.UserBankInfoMapper;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.IMoneyWithdrawService;
import cn.wolfcode.p2p.business.service.IUserBankInfoService;

@Service
@Transactional
public class UserBankInfoServiceImpl implements IUserBankInfoService {
	@Autowired
	private UserBankInfoMapper ubiMapper;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountFlowService accountFlowService;
	@Autowired
	private IMoneyWithdrawService mwService;

	@Override
	public void save(UserBankInfo record) {
		ubiMapper.insert(record);

	}

	@Override
	public void apply(BigDecimal moneyAmount) {

		UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
		Account borrowAccount = accountService.get(userInfo.getId());
		UserBankInfo userBankInfo = ubiMapper.selectByUserId(userInfo.getId());
		// 判断有没有在审核中
		// 绑定了银行卡
		// 最小提现金额<当前提现金额<账户可用余额
		if (!userInfo.getHasMoneyWithdrawInProcess() && userInfo.getHasBindBank()
				&& moneyAmount.compareTo(borrowAccount.getUsableAmount()) <= 0
				&& moneyAmount.compareTo(Constants.MIN_WITHDRAW_AMOUNT) >= 0) {
			// 创建提现对象设置相关属性
			MoneyWithdraw mw = new MoneyWithdraw();
			mw.setAccountName(userBankInfo.getAccountName());
			mw.setAccountNumber(userBankInfo.getAccountNumber());
			mw.setApplier(UserContext.getCurrent());
			mw.setApplyTime(new Date());
			mw.setBankForkName(userBankInfo.getBankForkName());
			mw.setBankName(userBankInfo.getBankName());
			mw.setFee(Constants.MONEY_WITHDRAW_CHARGEFEE);
			mw.setMoneyAmount(moneyAmount);
			// 账户可用余额减少 冻结金额增加
			borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(moneyAmount));
			borrowAccount.setFreezedAmount(borrowAccount.getUsableAmount().add(moneyAmount));
			// 生成提现申请流水
			accountFlowService.createMoneyWithdrawFlow(borrowAccount, mw);
			// 添加状态码
			userInfo.addState(BitStateUtil.HAS_MONEY_WITHDRWA_IN_PROCESS);
			userInfoService.update(userInfo);
			accountService.update(borrowAccount);
			mwService.save(mw);
		}
	}

	@Override
	public void bind(UserBankInfo ub) {
		// 判断有没有绑定
		UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
		if (ub != null && !userInfo.getHasBindBank()) {
			// 设置信息并保存
			UserBankInfo userBankInfo = new UserBankInfo();
			userBankInfo.setAccountName(userInfo.getRealName());
			userBankInfo.setAccountNumber(ub.getAccountNumber());
			userBankInfo.setBankForkName(ub.getBankForkName());
			userBankInfo.setBankName(ub.getBankName());
			userBankInfo.setUserInfoId(UserContext.getCurrent().getId());
			this.save(userBankInfo);
			// 添加状态码
			userInfo.addState(BitStateUtil.HAS_BINK_BANK);
			userInfoService.update(userInfo);
		} else {
			throw new DisplayableException("参数异常,请重试");
		}
	}

	@Override
	public UserBankInfo selectByUserId(Long userId) {
		return ubiMapper.selectByUserId(userId);
	}

}
