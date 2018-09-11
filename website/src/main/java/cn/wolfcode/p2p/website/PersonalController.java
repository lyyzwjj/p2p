package cn.wolfcode.p2p.website;

import java.math.BigDecimal;

import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import cn.wolfcode.p2p.business.service.ICreditTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.RequireLogin;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.domain.UserBankInfo;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.business.service.IRechargeOfflineService;
import cn.wolfcode.p2p.business.service.IUserBankInfoService;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Controller
public class PersonalController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IPlatformBankInfoService platformBankInfoService;
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;
	@Autowired
	private IUserBankInfoService userbankInfoSevice;

	@RequestMapping("personal")
	@RequireLogin
	public String personalPage(Model model) {
		model.addAttribute("account", accountService.get(UserContext.getCurrent().getId()));
		return "personal";
	}

	@RequestMapping("basicInfo")
	public String BasicInfoPage(Model model) {
		model.addAttribute("userInfo", userInfoService.get(UserContext.getCurrent().getId()));
		model.addAttribute("educationBackgrounds", userInfoService.listBySn("houseCondition"));
		model.addAttribute("incomeGrades", userInfoService.listBySn("incomeGrade"));
		model.addAttribute("marriages", userInfoService.listBySn("marriage"));
		model.addAttribute("kidCounts", userInfoService.listBySn("kidCount"));
		model.addAttribute("houseConditions", userInfoService.listBySn("houseCondition"));
		return "basicInfo";
	}

	@RequestMapping("basicInfo_save")
	@ResponseBody
	public JSONResult saveBasicInfo(UserInfo to) {
		JSONResult result = null;
		try {
			userInfoService.saveBasicInfo(to);
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

	@RequestMapping("recharge")
	public String rechargePage(Model model) {
		model.addAttribute("banks", platformBankInfoService.list());
		return "recharge";
	}

	@RequestMapping("recharge_save")
	@ResponseBody
	public JSONResult saveRecharge(RechargeOffline to) {
		JSONResult result = null;
		try {
			rechargeOfflineService.saveRecharge(to);
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

	@RequestMapping("bankInfo")
	public String bankInfoPage(Model model) {
		UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("bankInfo", userbankInfoSevice.selectByUserId(UserContext.getCurrent().getId()));
		if (userInfo.getHasBindBank()) {
			return "bankInfo_result";
		}
		return "bankInfo";
	}

	@RequestMapping("bankInfo_save")
	@ResponseBody
	public JSONResult userBankInfoSave(UserBankInfo ub) {
		JSONResult result = null;
		try {
			userbankInfoSevice.bind(ub);
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

	@RequestMapping("moneyWithdraw")
	public String moneyWithdrawPage(Model model) {
		model.addAttribute("userInfo", userInfoService.get(UserContext.getCurrent().getId()));
		model.addAttribute("account", accountService.get(UserContext.getCurrent().getId()));
		model.addAttribute("bankInfo", userbankInfoSevice.selectByUserId(UserContext.getCurrent().getId()));
		return "moneyWithdraw_apply";
	}

	@RequestMapping("moneyWithdraw_apply")
	@ResponseBody
	public JSONResult moneyWithdrawApply(BigDecimal moneyAmount) {
		JSONResult result = null;
		try {
			userbankInfoSevice.apply(moneyAmount);
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
