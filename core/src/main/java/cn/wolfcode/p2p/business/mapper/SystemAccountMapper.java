package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.SystemAccount;

public interface SystemAccountMapper {

    SystemAccount selectCurrent();

    int updateByPrimaryKey(SystemAccount record);
}