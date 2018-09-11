package cn.wolfcode.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/7/23.
 */

@Setter@Getter
public class SystemDictionaryItem extends BaseDomain{
	private static final long serialVersionUID = 1L;
	//只是用到了他们的Id,所以不用写他们得关联对象
    private Long parentId;//分类ID
    private String title;//名称
    private int sequence;//顺序
    public String getJsonString() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getId());
		map.put("parentId", this.parentId);
		map.put("title", this.title);
		map.put("sequence", this.sequence);
		return JSON.toJSONString(map);
	}
}
