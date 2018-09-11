package cn.wolfcode.p2p.base.mapper;

import java.util.List;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;

public interface IpLogMapper {

    int insert(IpLog record);


    List<IpLog> queryDate(IpLogQueryObject qo);
}