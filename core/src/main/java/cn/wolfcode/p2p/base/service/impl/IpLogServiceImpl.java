package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.mapper.IpLogMapper;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.service.IIpLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by WangZhe on 2018/7/23.
 */
@Service
public class IpLogServiceImpl implements IIpLogService {
    @Autowired
    private IpLogMapper ipLogMapper;

    @Override
    public int save(IpLog ipLog) {
        return ipLogMapper.insert(ipLog);
    }

    @Override
    public PageInfo<IpLog> queryPage(IpLogQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<IpLog> result = ipLogMapper.queryDate(qo);
        return new PageInfo<>(result);
    }

}
