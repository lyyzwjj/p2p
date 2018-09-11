package cn.wolfcode.p2p.website;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.business.service.IPaymentScheduleService;

@Controller
public class BorrowController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;

    @RequestMapping("borrow")
    public String borrowIndex(Model model) {
        if (UserContext.getCurrent() == null) {
            return "redirect:borrowIndex.html";
        } else {
            model.addAttribute("userInfo", userInfoService.get(UserContext.getCurrent().getId()));
            model.addAttribute("account", accountService.get(UserContext.getCurrent().getId()));
            return "/borrow";
        }
    }

    @RequestMapping("borrowInfo")
    public String borrowApplyPage(Model model) {
        UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
        if (userInfo.getHasBidRequestInProcess()) {
            return "borrow_apply_result";
        } else {
            if (userInfo.getHasBasicInfo() && userInfo.getHasRealAuth() && userInfo.getHasVideoAuth()) {
                model.addAttribute("minBidRequestAmount", Constants.BORROW_AMOUNT_MIN);
                Account account = accountService.get(UserContext.getCurrent().getId());
                model.addAttribute("account", account);
                model.addAttribute("minBidAmount", Constants.BID_AMOUNT_MIN);
                return "borrow_apply";
            } else {
                return "/redirect:borrow.ftl";
            }
        }
    }

    @RequestMapping("/borrow_apply")
    @ResponseBody
    public JSONResult borrowApply(BidRequest to) {
        JSONResult result = null;
        try {
            bidRequestService.apply(to);
            result = new JSONResult("申请成功,请等待审核");
        } catch (DisplayableException ex) {
            ex.printStackTrace();
            return new JSONResult(false, ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(false, "系统异常,请与管理员联系");
        }
        return result;
    }



    @RequestMapping("borrow_info")
    public String borrowInfo(Model model, Long id) {
        BidRequest bidRequest = bidRequestService.get(id);
        model.addAttribute("bidRequest", bidRequest);
        if (bidRequest != null && bidRequest.getBidRequestState() != Constants.BIDREQUEST_STATE_APPLY) {
            LoginInfo current = UserContext.getCurrent();
            UserInfo userInfo = userInfoService.get(bidRequest.getCreateUser().getId());
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("realAuth", realAuthService.get(userInfo.getRealAuthId()));
            if (current != null) {
                if (current.getId().equals(bidRequest.getCreateUser().getId())) {
                    model.addAttribute("self", true);
                } else {
                    model.addAttribute("account", accountService.get(current.getId()));
                }
            }
        }
        return "borrow_info";
    }

    @RequestMapping("borrow_bid")
    @ResponseBody
    public JSONResult borrowBid(BigDecimal amount, Long bidRequestId) {
        JSONResult result = null;
        try {
            bidRequestService.bid(amount, bidRequestId);
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
    @RequestMapping("borrowBidReturn_list")
    public String borrowReturnListPage(Model model,@ModelAttribute("qo")PaymentScheduleQueryObject qo) {
    	qo.setUserId(UserContext.getCurrent().getId());
    	model.addAttribute("pageResult", paymentScheduleService.query(qo));
    	model.addAttribute("account", accountService.get(UserContext.getCurrent().getId()));
    	return "returnmoney_list";
    }
    @RequestMapping("returnMoney")
    @ResponseBody
    public JSONResult returnMoney(Long id) {
    	JSONResult result = null;
		try {
			bidRequestService.returnMoney(id);
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
