package cn.wolfcode.p2p.base.mapper;

import java.util.List;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;

public interface RealAuthMapper {

    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

	List<RealAuth> queryData(RealAuthQueryObject qo);
}