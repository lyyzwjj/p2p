package cn.wolfcode.p2p.base.util;

import cn.wolfcode.p2p.base.vo.VerifyCodeVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by WangZhe on 2018/7/22.
 */
public class VerifyCodeContext {
    public static final String VERIFYCODE_IN_SESSION = "verifyCode";

    /**
     * 获取到request
     * @return
     */
    public static HttpServletRequest getRequest(){
        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 在session中放入当前验证码对象
     * @param verifyCode
     */
    public static void setVerifyCode(VerifyCodeVo verifyCode){
        getSession().setAttribute(VERIFYCODE_IN_SESSION,verifyCode);
    }

    /**
     * 从session中获取到当前验证码对象
     * @return
     */
    public static VerifyCodeVo getCurrentVerifyCode(){
        return (VerifyCodeVo) getSession().getAttribute(VERIFYCODE_IN_SESSION);
    }
}
