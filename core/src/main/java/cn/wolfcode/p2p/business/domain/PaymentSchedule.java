package cn.wolfcode.p2p.business.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.util.Constants;
import lombok.Getter;
import lombok.Setter;

//还款计划:记录借款每月的还款信息,针对于借款用户的
@Getter @Setter
public class PaymentSchedule extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private Date payDate;//还款日期
    private Date deadLine;//本期还款截止日期
    private BigDecimal totalAmount = Constants.ZERO;//本期还款金额
    private BigDecimal principal = Constants.ZERO;//本期还款本金
    private BigDecimal interest = Constants.ZERO;//本期还款利息
    private int monthIndex;//第几期还款(第几个月)
    private int state = Constants.PAYMENT_STATE_NORMAL;//本期还款状态

    private List<PaymentScheduleDetail> details = new ArrayList<>();//包含的还款计划明细

    private Long bidRequestId;//借款对象
    private LoginInfo borrowUser;//借款人/还款人
    private String bidRequestTitle;//借款标题
    private int bidRequestType;//借款类型
    private int returnType;//还款方式

    public String getStateDisplay () {
        switch (state) {
            case Constants.PAYMENT_STATE_NORMAL:
                return "正常待还";
            case Constants.PAYMENT_STATE_DONE:
                return "已还";
            case Constants.PAYMENT_STATE_OVERDUE:
                return "逾期";
            default:
                return "未知";
        }
    }
}
