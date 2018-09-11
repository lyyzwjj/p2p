package cn.wolfcode.p2p.website;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import cn.wolfcode.p2p.business.service.ICreditTransferService;
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
public class CreditTransferController {
    @Autowired
    private ICreditTransferService creditTransferService;
    @RequestMapping("listCanTransferCredit")
    public String TransferCreditPage(Model model, @ModelAttribute("qo") CreditTransferQueryObject qo){
        model.addAttribute("pageResult",creditTransferService.listCanTransferCredit(qo));
        return "/credittrans/credittransfer_cantrans_list";
    }
    @RequestMapping("creditTransfer")
    @ResponseBody
    public JSONResult creditTransfer(Long[] bidId){
        JSONResult result = null;
                try {
                    creditTransferService.creditTransfer(bidId);
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
