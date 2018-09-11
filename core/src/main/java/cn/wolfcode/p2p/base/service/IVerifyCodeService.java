package cn.wolfcode.p2p.base.service;

/**
 * Created by WangZhe on 2018/7/22.
 */
public interface IVerifyCodeService {
    /**
     * 发送验证码
     * @param phoneNumber
     */
    void  sendVerifyCode(String phoneNumber);
}
