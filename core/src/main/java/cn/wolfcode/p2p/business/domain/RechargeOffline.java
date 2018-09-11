package cn.wolfcode.p2p.business.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;

import cn.wolfcode.p2p.base.domain.AuthBaseDomain;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RechargeOffline extends AuthBaseDomain {
	private static final long serialVersionUID = 1L;
	private PlatformBankInfo bankInfo;
	private String tradeCode;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date rechargeDate;
	private BigDecimal amount;
	private String note;
	public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",super.getId());
        map.put("username",applier.getUsername());
        map.put("tradeCode",tradeCode);
        map.put("amount",amount);
        map.put("rechargeDate",DateFormat.getDateInstance().format(rechargeDate));
        return JSON.toJSONString(map);
    }
}
