package cn.wolfcode.p2p.business.mapper;

import java.util.List;

import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.query.RechargeOfflineQueryObject;

public interface RechargeOfflineMapper {

    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeOffline record);

	List<RechargeOffline> queryData(RechargeOfflineQueryObject qo);

}