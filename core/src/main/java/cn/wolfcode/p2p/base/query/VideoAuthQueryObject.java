package cn.wolfcode.p2p.base.query;

import com.alibaba.druid.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Getter
@Setter
public class VideoAuthQueryObject extends AuthQueryObject {
    private String username;
    public String getUsername(){
        return StringUtils.isEmpty(username) ? null : username;
    }
    /*public String getBeginDateDisplay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(this.beginDate);
	}
    public String getEndDateDisplay() {
    	if(endDate == null) {
    		return null;
    	}
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	return formatter.format(this.endDate);
    }*/
}
