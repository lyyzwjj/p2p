package cn.wolfcode.p2p.business.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.wolfcode.p2p.base.domain.AuthBaseDomain;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MoneyWithdraw extends AuthBaseDomain{
	private static final long serialVersionUID = 1L;
	private String bankName;
	private String accountName;
	private String accountNumber;
	private String bankForkName;
	private BigDecimal moneyAmount;
	private BigDecimal fee;
	public String getJsonString() {
		Map<String,Object> json = new HashMap<>(); 
		json.put("id", this.getId());
		json.put("username", this.getApplier().getUsername());
		json.put("realName", this.accountName);
		json.put("applyTime", DateFormat.getDateTimeInstance().format(this.getApplyTime()));
		json.put("accountNumber", this.accountNumber);
		json.put("bankName", this.bankName);
		json.put("bankForkName", this.bankForkName);
		json.put("moneyAmount", this.moneyAmount);
		return JSON.toJSONString(json);
	}
}
