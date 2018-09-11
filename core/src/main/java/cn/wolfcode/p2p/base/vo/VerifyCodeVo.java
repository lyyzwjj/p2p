package cn.wolfcode.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter@Getter
public class VerifyCodeVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String phoneNumber;//手机号码
    private String verifyCode;//随机码
    private Date sendTime;//发送时间
}
