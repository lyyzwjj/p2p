package cn.wolfcode.p2p.base.util;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 */
public class BitStateUtil {
    public final static Long HAS_BIND_EMAIL = 1L << 0; // 用户绑定邮箱状态码
    public final static Long HAS_BASICINFO = 1L << 1; // 用户填写基本资料状态码
    public final static Long HAS_REALAUTH = 1L << 2; // 用户实名认证状态码
    public final static Long HAS_VIDEOAUTH = 1L << 3; // 用户视频认证状态码
    public static final long HAS_BIDREQUEST_PROCESS = 1L<<4;//正在借款的流程申请
    public final static Long HAS_BINK_BANK = 1L << 5; // 用户绑定银行卡状态码
    public final static Long HAS_MONEY_WITHDRWA_IN_PROCESS = 1L << 6; // 用户正在提现流程状态码

    /**
     * @param states 所有状态值
     * @param value  需要判断状态值
     * @return 是否存在
     */
    public static boolean hasState(long states, long value) {
        return (states & value) != 0;
    }

    /**
     * @param states 已有状态值
     * @param value  需要添加状态值
     * @return 新的状态值
     */
    public static long addState(long states, long value) {
        if (hasState(states, value)) {
            return states;
        }
        return (states | value);
    }

    /**
     * @param states 已有状态值
     * @param value  需要删除状态值
     * @return 新的状态值
     */
    public static long removeState(long states, long value) {
        if (!hasState(states, value)) {
            return states;
        }
        return states ^ value;
    }
}
