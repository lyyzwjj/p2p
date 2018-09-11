package cn.wolfcode.p2p.base.util;


import cn.wolfcode.p2p.base.domain.LoginInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by WangZhe on 2018/7/22.
 */
public class UserContext {
    public static final String USER_IN_SESSION = "loginInfo";

    /**
     * 获取到request
     * @return
     */
    public static HttpServletRequest getRequest(){
      HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
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
     * 在session中放入当前用户
     * @param loginInfo
     */
    public static void setCurrent(LoginInfo loginInfo){
        getSession().setAttribute(USER_IN_SESSION,loginInfo);
    }

    /**
     * 从session中获取到当前用户
     * @return
     */
    public static LoginInfo getCurrent(){
        return (LoginInfo) getSession().getAttribute(USER_IN_SESSION);
    }

    /**
     * 获取用户登录ip
     * @return
     */
    public static String  getIp(){
        return getRequest().getRemoteAddr();
    }
}
