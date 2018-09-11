package cn.wolfcode.p2p.base.query;

import com.alibaba.druid.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SystemDictionaryQueryObject extends QueryObject {
	private String keyword;
	private Long parentId = 0L;
	public String getKeyword() {
		return StringUtils.isEmpty(keyword) ? null : keyword;
	}
}
