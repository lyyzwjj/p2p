package cn.wolfcode.p2p.base.mapper;

import org.apache.ibatis.annotations.Param;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.util.List;

public interface LoginInfoMapper {

    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);


    int updateByPrimaryKey(LoginInfo record);

    int queryCountByUsername(String username);

    LoginInfo login(@Param("username") String username, @Param("password") String password,@Param("userType") int userType);

    List<LoginInfo> listAuditors();
}