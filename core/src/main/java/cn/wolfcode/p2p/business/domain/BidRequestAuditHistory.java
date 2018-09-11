package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.AuthBaseDomain;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BidRequestAuditHistory extends AuthBaseDomain{
	private static final long serialVersionUID = 1L;
	public static final int AUDIT_PUBLISH = 0;	//发表前审核
	public static final int AUDIT_FULL1 = 1;	//满标一审
	public static final int AUDIT_FULL2 = 2;	//满标二审
	private Long bidRequestId;
	private int auditType;

}
