package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.util.List;

/**
 * Created by WangZhe on 2018/7/20.
 */
public interface ILoginInfoService {

    void register(String username, String verifycode, String password, String confirmPwd);

    boolean checkUsername(String username);

    LoginInfo login(String username, String password,int UserType);

    List<LoginInfo> listAuditors();
}
