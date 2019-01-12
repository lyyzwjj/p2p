package cn.wolfcode.p2p.mgrsite;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 后台发标控制
 * <p>
 * Created by WangZhe on 2018/7/27.
 */
@Controller
public class BidRequestController {

    @Autowired
    private IBidRequestService bidRequestService;

    @RequestMapping("bidrequest_publishaudit_list")
    public String bidRequstAuditPage(Model model, @ModelAttribute("qo") BidRequestQueryObject qo) {
        qo.setCurrentPage(1);
        qo.setPageSize(5);
        model.addAttribute("loginInfo", UserContext.getCurrent());
        model.addAttribute("pageResult", bidRequestService.queryInvestList(qo));
        return "bidrequest/publish_audit";
    }

    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JSONResult publishAudit(Long id, String remark, int state, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date publishTime) {
        JSONResult result = null;
        try {
            bidRequestService.publishAudit(id, remark, state, publishTime);
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

    @RequestMapping("bidrequest_audit1_list")
    public String fullAudit1Page(Model model, @ModelAttribute("qo") BidRequestQueryObject qo) {
        qo.setCurrentPage(1);
        qo.setPageSize(5);
        qo.setState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
        model.addAttribute("loginInfo", UserContext.getCurrent());
        model.addAttribute("pageResult", bidRequestService.queryInvestList(qo));
        return "/bidrequest/audit1";
    }

    @RequestMapping("bidrequest_audit2_list")
    public String fullAudit2Page(Model model, @ModelAttribute("qo") BidRequestQueryObject qo) {
        qo.setCurrentPage(1);
        qo.setPageSize(5);
        qo.setState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
        model.addAttribute("loginInfo", UserContext.getCurrent());
        model.addAttribute("pageResult", bidRequestService.queryInvestList(qo));
        return "/bidrequest/audit2";
    }

    @RequestMapping("bidrequest_audit1")
    @ResponseBody
    public JSONResult fullAudit1(Long id, String remark, int state) {
        JSONResult result;
        try {
            bidRequestService.fullAudit1(id, remark, state);
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

    @RequestMapping("bidrequest_audit2")
    @ResponseBody
    public JSONResult fullAudit2(Long id, String remark, int state) {
        JSONResult result;
        try {
            bidRequestService.fullAudit2(id, remark, state);
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
