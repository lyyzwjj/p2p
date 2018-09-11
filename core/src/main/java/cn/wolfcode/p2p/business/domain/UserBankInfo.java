package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserBankInfo extends BaseDomain{
	private static final long serialVersionUID = 1L;
	private Long userInfoId;
	private String bankName;
	private String accountName;
	private String accountNumber;
	private String bankForkName;

}
