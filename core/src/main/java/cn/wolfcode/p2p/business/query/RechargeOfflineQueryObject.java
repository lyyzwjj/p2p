package cn.wolfcode.p2p.business.query;

import com.alibaba.druid.util.StringUtils;

import cn.wolfcode.p2p.base.query.AuthQueryObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RechargeOfflineQueryObject extends AuthQueryObject{
	private Long bankInfoId;
	private String tradeCode;
	public String getTradeCode() {
		return StringUtils.isEmpty(this.tradeCode) ? null : this.tradeCode;
	}
}
