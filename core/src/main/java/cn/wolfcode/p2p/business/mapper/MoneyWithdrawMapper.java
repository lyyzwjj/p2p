package cn.wolfcode.p2p.business.mapper;

import java.util.List;

import cn.wolfcode.p2p.business.domain.MoneyWithdraw;
import cn.wolfcode.p2p.business.query.MoneyWithdrawQueryObject;

public interface MoneyWithdrawMapper {
	int insert(MoneyWithdraw record);

	MoneyWithdraw selectByPrimaryKey(Long id);

	int updateByPrimaryKey(MoneyWithdraw record);

	List<MoneyWithdraw> queryData(MoneyWithdrawQueryObject qo);
}