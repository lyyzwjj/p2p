package cn.wolfcode.p2p.business.service;

import java.math.BigDecimal;

import cn.wolfcode.p2p.business.domain.UserBankInfo;

public interface IUserBankInfoService {
	void save(UserBankInfo record);

	void bind(UserBankInfo ub);
	
	UserBankInfo selectByUserId(Long userId);

	void apply(BigDecimal moneyAmount);
}
