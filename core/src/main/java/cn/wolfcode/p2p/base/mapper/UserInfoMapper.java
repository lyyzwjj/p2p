package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.UserInfo;

public interface UserInfoMapper {

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);
    
    int update(UserInfo record);
}