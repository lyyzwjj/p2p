package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.business.query.MoneyWithdrawQueryObject;
import cn.wolfcode.p2p.business.service.IMoneyWithdrawService;

@Controller
public class MoneyWithdrawController {
	@Autowired
	private IMoneyWithdrawService mwService;
	@RequestMapping("moneyWithdraw")
	public String moneyWithdrawPage(Model model,@ModelAttribute("qo")MoneyWithdrawQueryObject qo) {
		model.addAttribute("pageResult", mwService.query(qo));
		return "moneywithdraw/list";
	}
	@RequestMapping("moneyWithdraw_audit")
	@ResponseBody
	public JSONResult audit(Long id,int state, String remark) {
		JSONResult result = null;
		try {
			mwService.audit(id,state,remark);
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
