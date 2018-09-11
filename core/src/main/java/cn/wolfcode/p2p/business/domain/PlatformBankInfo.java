package cn.wolfcode.p2p.business.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformBankInfo extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String bankName;
	private String accountName;
	private String accountNumber;
	private String bankForkName;

	public String getJsonString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getId());
		map.put("bankName", this.bankName);
		map.put("accountName", this.accountName);
		map.put("accountNumber", this.accountNumber);
		map.put("bankForkName", this.bankForkName);
		return JSON.toJSONString(map);
	}
}
