package cn.wolfcode.p2p.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.wolfcode.p2p.base.util.UserContext;
@Component
public class LoginCheckInteceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler != null && handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			if (hm.hasMethodAnnotation(RequireLogin.class) && UserContext.getCurrent() == null) {
				response.sendRedirect("/login.html");
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
