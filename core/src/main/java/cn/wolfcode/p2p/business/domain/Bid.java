package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次投标对象
 * <p>
 * Created by WangZhe on 2018/7/27.
 */
@Getter
@Setter
public class Bid extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private BigDecimal actualRate;//年华利率
    private BigDecimal availableAmount;//投标金额
    private Long bidRequestId;//借款对象
    private String bidRequestTitle;//借款标题
    private LoginInfo bidUser;//投标人
    private Date bidTime;//投标时间
    private int bidRequestState;//借款的状态
}
