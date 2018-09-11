package cn.wolfcode.p2p.base.service;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;

/**
 * Created by WangZhe on 2018/7/23.
 */
public interface IIpLogService {
    int save(IpLog ipLog);

    PageInfo<IpLog> queryPage(IpLogQueryObject qo);
}
