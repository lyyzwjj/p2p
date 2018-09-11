package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BidRequestQueryObject extends QueryObject {
	private int state = -1;
	private int[] states;
	private String orderBy;
	private String orderType;
	private Date publishTime;
	private int bidRequestType;
}
