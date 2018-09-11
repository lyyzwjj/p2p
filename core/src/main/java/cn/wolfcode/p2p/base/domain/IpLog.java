package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Getter@Setter
public class IpLog extends BaseDomain{
	private static final long serialVersionUID = 1L;
	public static final int STATE_FAILED = 0;
    public static final int STATE_SUCCESS = 1;
    private String username;//登陆用户
    private Date loginTime;//登陆时间
    private String ip;//来源IP
    private int state;//登陆状态
    private int userType;
    public String getUsername() {
    	return StringUtils.isEmpty(username)?null :username;
    }
    public String getStateDisplay(){
        return state == STATE_SUCCESS ? "登录成功":"登录失败";
    }
    public String getUserTypeDisplay() {
    	return this.userType == 0 ? "前台用户":"后台管理员";
    }
}
