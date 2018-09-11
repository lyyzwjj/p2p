package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;

@Controller
public class PlatformBankInfoController {
	@Autowired
	private IPlatformBankInfoService piService;
	@RequestMapping("companyBank_list")
	public String bankInfoPage(Model model) {
		model.addAttribute("pageResult", piService.list());
		return "/platformbankinfo/list";
	}
	@RequestMapping("companyBank_update")
	@ResponseBody
	public JSONResult saveBankInfo(PlatformBankInfo pi) {
		JSONResult result = null;
		try {
			piService.saveOrUpdate(pi);
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
