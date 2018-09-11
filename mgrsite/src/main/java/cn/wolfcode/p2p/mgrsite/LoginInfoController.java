package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UserContext;

/**
 * Created by WangZhe on 2018/7/20.
 * 后台登陆页面
 */
@Controller
public class LoginInfoController {
	@Autowired
	private ILoginInfoService loginInfoService;


	@RequestMapping("main")
	public String mian() {
		return "main";
	}

	@RequestMapping("userLogin")
	@ResponseBody
	public JSONResult login(String username, String password) {
		JSONResult result = null;
		try {
			LoginInfo loginInfo = loginInfoService.login(username, password, LoginInfo.USER_TYPE_MANAGER);
			if (loginInfo == null) {
				throw new DisplayableException("账户或者密码错误,请重新输入");
			}
			result = new JSONResult("登陆成功");
			UserContext.setCurrent(loginInfo);
		} catch (DisplayableException ex) {
			ex.printStackTrace();
			return new JSONResult(false, ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONResult(false, "系统异常,请与管理员联系");
		}
		return result;
	}
}
