package cn.wolfcode.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountFlow extends BaseDomain{
	private static final long serialVersionUID = 1L;
	private Date actionTime;
	private BigDecimal amount;
	private int actionType;
	private Long accountId;
	private String note;
	private BigDecimal usableAmount;
	private BigDecimal freezedAmount;
}
