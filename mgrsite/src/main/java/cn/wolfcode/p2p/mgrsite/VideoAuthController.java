package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.base.util.JSONResult;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Controller
public class VideoAuthController {
    @Autowired
    private IVideoAuthService videoAuthService;
    @RequestMapping("videoAuth")
    public String videoAuthPage(Model model, @ModelAttribute("qo")VideoAuthQueryObject qo){
        model.addAttribute("pageResult",videoAuthService.query(qo));
        return "/videoAuth/list";
    }
    @RequestMapping("videoAuth_audit")
    @ResponseBody
    public JSONResult auditVideoAuth(Long id,int state,String remark){
        JSONResult result = null;
                try {
                  videoAuthService.audit(id,state,remark);
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
