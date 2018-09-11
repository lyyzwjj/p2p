package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Getter
@Setter
public class AuthQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date endDate;
    protected int state = -1;
    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }
}
