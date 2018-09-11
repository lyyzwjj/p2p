package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter@Getter
public class SystemAccount extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private int version;//乐观锁
    private BigDecimal usableAmount = Constants.ZERO;//可用金额
    private BigDecimal freezedAmount = Constants.ZERO;//冻结金额
}
