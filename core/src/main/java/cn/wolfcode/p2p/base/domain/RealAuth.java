package cn.wolfcode.p2p.base.domain;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RealAuth extends AuthBaseDomain{
	private static final long serialVersionUID = 1L;
	public static final int SEX_MALE = 0;//性别男
    public static final int SEX_FEMALE = 1;//性别女
	private String realName;
	private int sex;
	private String idNumber;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date bornDate;
	private String address;
	private String image1;
	private String image2;
	public String getSexDisplay() {
		return this.sex == SEX_MALE ? "男":"女";
	}
	public String getBornDateDisplay() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(this.bornDate);
	}
	 public String getJsonString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getId());
		map.put("username", this.applier.getUsername());
		map.put("realName", this.realName);
		map.put("idNumber", this.idNumber);
		map.put("sex", this.getSexDisplay());
		map.put("bornDate", this.bornDate);
		map.put("address", this.address);
		map.put("image1", this.image1);
		map.put("image2", this.image2);
		return JSON.toJSONString(map);
		}
}
