package cn.wolfcode.p2p.website;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.CreditTransfer;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;
import cn.wolfcode.p2p.business.service.ICreditTransferService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by WangZhe on 2018/8/3.
 */
@Controller
public class InvestController {
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private ICreditTransferService creditTransferService;

    @RequestMapping("invest_list")
    public String query(Model model, @ModelAttribute("qo") BidRequestQueryObject qo) {
        if (qo.getBidRequestType() == Constants.BIDREQUEST_TYPE_NORMAL) {
            if (qo.getState() == -1) {
                qo.setStates(new int[]{Constants.BIDREQUEST_STATE_BIDDING, Constants.BIDREQUEST_STATE_PAYING_BACK, Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
            }
            PageInfo<BidRequest> pageResult = bidRequestService.queryInvestList(qo);
            model.addAttribute("pageResult", pageResult);
            return "invest_list";
        } else {
            CreditTransferQueryObject cto = new CreditTransferQueryObject();
            cto.setCurrentPage(qo.getCurrentPage());
            cto.setPageSize(qo.getPageSize());
            cto.setState(Constants.BIDREQUEST_STATE_BIDDING);
            PageInfo<CreditTransfer> pageResult = creditTransferService.query(cto);
            model.addAttribute("pageResult", pageResult);
            return "/credittrans/credittransfer_list";

        }
    }

    @RequestMapping("invest")
    public String invest() {
        return "invest";
    }
    @RequestMapping("subscribe")
    @ResponseBody
    public JSONResult subscribe(Long id){
        JSONResult result = null;
                try {
                    creditTransferService.subscribe(id);
                    result = new JSONResult("登陆成功");
                } catch (DisplayableException ex) {
                    ex.printStackTrace();
                    return new JSONResult(false,ex.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                    return new JSONResult(false,"系统异常,请与管理员联系");
                }
                return result;
    }
}
