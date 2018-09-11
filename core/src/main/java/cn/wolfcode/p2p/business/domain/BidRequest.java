package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.util.Constants;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 借款对象模型
 *
 * Created by WangZhe on 2018/7/27.
 */
@Getter
@Setter
public class BidRequest extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private int version;//乐观锁
    private int returnType;//还款方式(按月分期)
    private int bidRequestType;//借款类型(信用贷)
    private int bidRequestState;//借款状态
    private BigDecimal bidRequestAmount;//借款金额
    private BigDecimal currentRate;//年华利率
    private BigDecimal minBidAmount;//最小投标金额
    private int monthes2Return;//借款期限
    private int bidCount;//投标个数
    private BigDecimal totalRewardAmount;//借款总利息
    private BigDecimal currentSum = Constants.ZERO;//当前已投金额
    private String title;//借款标题
    private String description;//借款描述
    private String note;//风控意见
    private Date disableDate;//招标截止时间
    private int disableDays;//招标天数
    private LoginInfo createUser;//借款人
    private List<Bid> bids = new ArrayList<Bid>();//投标集合
    private Date applyTime;//申请时间
    private Date publishTime;//发布时间

    public BigDecimal getRemainBidAmount() {
        return bidRequestAmount.subtract(getCurrentSum());
    }

    public String getReturnTypeDisplay() {
        return returnType == Constants.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL ? "按月分期" : "按月到期";
    }

    public int getPersent() {
        return this.getCurrentSum().divide(getBidRequestAmount(), 4, RoundingMode.HALF_UP).multiply(Constants.HUNDRED).intValue();
    }

    public String getJsonString() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", super.getId());
        map.put("username", getCreateUser().getUsername());
        map.put("title", title);
        map.put("bidRequestAmount", bidRequestAmount);
        map.put("currentRate", currentRate);
        map.put("monthes2Return", monthes2Return);
        map.put("returnType", returnType);
        map.put("totalRewardAmount", totalRewardAmount);
        return JSON.toJSONString(map);
    }

    public String getBidRequestStateDisplay() {
        switch (bidRequestState) {
            case Constants.BIDREQUEST_STATE_APPLY:
                return "待审核";
            case Constants.BIDREQUEST_STATE_PUBLISH_PENDING:
                return "待发布";
            case Constants.BIDREQUEST_STATE_BIDDING:
                return "招标中";
            case Constants.BIDREQUEST_STATE_UNDO:
                return "已撤销";
            case Constants.BIDREQUEST_STATE_BIDDING_OVERDUE:
                return "流标";
            case Constants.BIDREQUEST_STATE_APPROVE_PENDING_1:
                return "满标一审";
            case Constants.BIDREQUEST_STATE_APPROVE_PENDING_2:
                return "满标二审";
            case Constants.BIDREQUEST_STATE_REJECTED:
                return "满标审核拒绝";
            case Constants.BIDREQUEST_STATE_PAYING_BACK:
                return "还款中";
            case Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK:
                return "已还清";
            case Constants.BIDREQUEST_STATE_PAY_BACK_OVERDUE:
                return "逾期";
            case Constants.BIDREQUEST_STATE_PUBLISH_REFUSE:
                return "发标审核拒绝";
        }
        return "未知";
    }
}
