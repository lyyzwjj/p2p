package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo extends BaseDomain {
	private static final long serialVersionUID = 1L;
	public static final int USER_TYPE_NORMAL = 0;
	public static final int USER_TYPE_MANAGER = 1;
	public static final int MANAGER_TYPE_NORMAL = 0;
	public static final int MANAGER_TYPE_VIDEOAUDITOR = 1;

	private String username;

	private String password;

	private int state;
	
	private int userType = USER_TYPE_NORMAL;

	private int videoAuditor = MANAGER_TYPE_NORMAL;
}