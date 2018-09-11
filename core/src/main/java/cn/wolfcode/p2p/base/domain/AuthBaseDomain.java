package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Getter
@Setter
public class AuthBaseDomain extends BaseDomain{
    private static final long serialVersionUID = 1L;
    public static final int STATE_NORMAL = 0;//待审核
    public static final int STATE_PASS = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核拒接
    protected LoginInfo auditor;
    protected LoginInfo applier;
    protected Date auditTime;
    protected Date applyTime;
    protected String remark;
    protected int state;
    public String getStateDisplay() {
        switch (state) {
            case STATE_NORMAL:return "待审核";
            case STATE_PASS : return "审核通过";
            case STATE_REJECT : return "审核拒绝";
            default: return "未知";
        }
    }
}
