package cn.wolfcode.p2p.website;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IVerifyCodeService;
import cn.wolfcode.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by WangZhe on 2018/7/22.
 */
@Controller
public class VerifyCodeController {
    @Autowired
    private IVerifyCodeService verifyCodeService;

    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber) {
        JSONResult result = null;
        try {
            verifyCodeService.sendVerifyCode(phoneNumber);
            result = new JSONResult("验证码发送成功");
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
