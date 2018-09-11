package cn.wolfcode.p2p.business.service;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.query.RechargeOfflineQueryObject;

public interface IRechargeOfflineService {
	void save(RechargeOffline rl);

	void update(RechargeOffline rl);

	void saveRecharge(RechargeOffline to);

	PageInfo<RechargeOffline> queryData(RechargeOfflineQueryObject qo);

	void audit(Long id, int state, String remark);

}
