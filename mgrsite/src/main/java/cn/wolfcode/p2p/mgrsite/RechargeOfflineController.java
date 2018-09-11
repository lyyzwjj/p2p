package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.query.RechargeOfflineQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.business.service.IRechargeOfflineService;

@Controller
public class RechargeOfflineController {
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;
	@Autowired
	private IPlatformBankInfoService pbiService;
	@RequestMapping("rechargeOffline")
	public String rechargeOfflinePage(Model model,@ModelAttribute("qo")RechargeOfflineQueryObject qo) {
		model.addAttribute("loginInfo",UserContext.getCurrent());
		model.addAttribute("pageResult", rechargeOfflineService.queryData(qo));
		model.addAttribute("banks",pbiService.list());
		return "/rechargeoffline/list";
	}
	@RequestMapping("rechargeOffline_audit")
	@ResponseBody
	public JSONResult rechargeOfflineAudit(Long id,int state,String remark) {
		JSONResult result = null;
		try {
			rechargeOfflineService.audit(id,state,remark);
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
