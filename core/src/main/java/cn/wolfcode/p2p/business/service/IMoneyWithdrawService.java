package cn.wolfcode.p2p.business.service;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.business.domain.MoneyWithdraw;
import cn.wolfcode.p2p.business.query.MoneyWithdrawQueryObject;

public interface IMoneyWithdrawService {
	void save(MoneyWithdraw record);

	PageInfo<MoneyWithdraw> query(MoneyWithdrawQueryObject qo);

	void audit(Long id, int state, String remark);
}
