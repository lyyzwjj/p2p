package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.util.JSONResult;

/**
 * 后台实名认证控制
 * 
 * @author WangZhe
 *
 */
@Controller
public class RealAuthController {
	@Autowired
	private IRealAuthService realAuthService;
	@RequestMapping("realAuth")
	public String realAuthPage(Model model,@ModelAttribute("qo")RealAuthQueryObject qo) {
		model.addAttribute("pageResult", realAuthService.query(qo));
		return "/realAuth/list";
	}
	@RequestMapping("realAuth_audit")
	@ResponseBody
	public JSONResult auditRealAuth(int state,Long id,String remark) {
		JSONResult result = null;
		try {
			realAuthService.audit(state,id,remark);
			result = new JSONResult();
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
