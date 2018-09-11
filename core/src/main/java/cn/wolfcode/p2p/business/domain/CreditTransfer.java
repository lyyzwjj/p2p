package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by WangZhe on 2018/8/3.
 */
@Getter
@Setter
public class CreditTransfer extends BaseDomain {
    private int version;// 乐观锁
    private Long bidId;// 对应投标id
    private Long bidRequestId;// 对应借款
    private BigDecimal bidRequestAmount;// 认购本金
    private BigDecimal currentRate;// 借款利息
    private int returnType;// 还款方式
    private int monthIndex;// 总还款期数
    private int remainMonthIndex;// 剩余还款期数
    private BigDecimal remainInterest;// 剩余利息
    private Date closestDeadLine;// 最近还款时间
    private String bidRequestTitle;// 原借款名称
    private int bidRequestState;// 借款状态
    private LoginInfo transFrom;// 转出人
    private LoginInfo transTo;// 接手人
    private Date transDate;// 接手时间
}
