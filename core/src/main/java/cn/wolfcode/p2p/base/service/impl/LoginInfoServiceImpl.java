package cn.wolfcode.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.AssertUtil;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.DateUtil;
import cn.wolfcode.p2p.base.util.MD5;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.base.util.VerifyCodeContext;
import cn.wolfcode.p2p.base.vo.VerifyCodeVo;

/**
 * Created by WangZhe on 2018/7/20.
 */
@Service
@Transactional
public class LoginInfoServiceImpl implements ILoginInfoService {
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IIpLogService ipLogService;


    @Override
    public void register(String username, String verifycode, String password, String confirmPwd) {
        AssertUtil.isNotNull(username, "用户名不能为空");
        AssertUtil.isNotNull(verifycode, "验证码不能为空");
        AssertUtil.isNotNull(password, "密码不能为空");
        AssertUtil.isNotNull(confirmPwd, "确认密码不能为空");
        if (!password.equals(confirmPwd)) {
            throw new DisplayableException("两次密码输入不一致");
        }
        VerifyCodeVo vo = VerifyCodeContext.getCurrentVerifyCode();
        if (vo != null &&
                vo.getVerifyCode().equalsIgnoreCase(verifycode) &&
                vo.getPhoneNumber().equals(username) &&
                (DateUtil.getTimeBetween(vo.getSendTime(), new Date()) / 60) < Constants.VERIFYCODE_VAILD_TIME
                ) {
            int count = loginInfoMapper.queryCountByUsername(username);
            if (count > 0) {
                throw new DisplayableException("用户名已被使用,请重新输入");
            }
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setUsername(username);
            loginInfo.setPassword(MD5.encode(password));
            loginInfoMapper.insert(loginInfo);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(loginInfo.getId());
            userInfo.setPhoneNumber(loginInfo.getUsername());
            userInfoService.save(userInfo);
            Account account = new Account();
            account.setId(loginInfo.getId());
            accountService.save(account);
        } else {
            throw new DisplayableException("验证码错误");
        }
    }

    @Override
    public boolean checkUsername(String username) {
        AssertUtil.isNotNull(username, "手机号码不能为空");
        return loginInfoMapper.queryCountByUsername(username) <= 0;
    }

    @Override
    public LoginInfo login(String username, String password,int userType) {
        password = MD5.encode(password);
        LoginInfo loginInfo = loginInfoMapper.login(username, password,userType);
        IpLog ipLog = new IpLog();
        if (loginInfo == null) {
            ipLog.setState(IpLog.STATE_FAILED);
        } else {
            ipLog.setState(IpLog.STATE_SUCCESS);
            
        }
        ipLog.setUsername(username);
        ipLog.setLoginTime(new Date());
        ipLog.setIp(UserContext.getIp());
        ipLogService.save(ipLog);
        return loginInfo;
    }

    @Override
    public List<LoginInfo> listAuditors() {
        return loginInfoMapper.listAuditors();
    }

}
