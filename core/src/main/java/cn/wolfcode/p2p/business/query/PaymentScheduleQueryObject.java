package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.AuthQueryObject;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PaymentScheduleQueryObject extends AuthQueryObject {
	private Long userId;
	private Long bidRequestId;
	
}
