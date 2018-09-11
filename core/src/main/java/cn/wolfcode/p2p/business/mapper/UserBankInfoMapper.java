package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.UserBankInfo;

public interface UserBankInfoMapper {
    int insert(UserBankInfo record);

    UserBankInfo selectByUserId(Long userInfoId);

}