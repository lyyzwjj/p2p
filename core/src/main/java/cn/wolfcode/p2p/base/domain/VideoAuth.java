package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Getter
@Setter
public class VideoAuth extends AuthBaseDomain{
	private static final long serialVersionUID = 1L;
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date orderDate;
    private OrderTime orderTime;
    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", this.getId());
        map.put("state",this.getState());
        map.put("applier", this.applier.getUsername());
        return JSON.toJSONString(map);
    }
}
