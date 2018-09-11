package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.druid.util.StringUtils;

import java.util.Date;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Getter@Setter
public class IpLogQueryObject extends QueryObject{
    private String username;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int userType = -1;
    private Integer state = -1;
    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
    public String getUsername() {
    	return StringUtils.isEmpty(username) ? null : username;
    }
}
