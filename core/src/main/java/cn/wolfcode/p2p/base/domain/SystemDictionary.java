package cn.wolfcode.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典实体类
 * Created by WangZhe on 2018/7/23.
 */
@Getter
@Setter
public class SystemDictionary extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String title;// 分类名称,如:教育背景
	private String sn;// 分类编码 ,如:educationBackground

	public String getJsonString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getId());
		map.put("sn", this.sn);
		map.put("title", this.title);
		return JSON.toJSONString(map);
	}
}
