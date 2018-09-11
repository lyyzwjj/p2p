package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.base.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Setter
@Getter
public class Account extends BaseDomain{
	private static final long serialVersionUID = 1L;
	
	private int version;//乐观锁
    private String tradePassword;//交易密码
    private BigDecimal usableAmount = Constants.ZERO;//可用金额
    private BigDecimal  freezedAmount = Constants.ZERO;//冻结金额
    private BigDecimal unReceiveInterest = Constants.ZERO;//待收利息
    private BigDecimal unReceivePrincipal = Constants.ZERO;//待收本金
    private BigDecimal unReturnAmount = Constants.ZERO;//待还金额
    private BigDecimal remainBorrowLimit = Constants.BORROWLIMIT;//剩余授信额度
    private BigDecimal borrowLimitAmount = Constants.BORROWLIMIT;//授信额度
    public BigDecimal getTotalAmount(){
        return this.usableAmount.add(this.freezedAmount).add(this.unReceivePrincipal);
    }
}
