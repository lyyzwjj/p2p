package cn.wolfcode.p2p.website;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.base.util.DateUtil;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UserContext;

/**
 * 前台用户视频审核控制
 * <p>
 * Created by WangZhe on 2018/7/27.
 */
@Controller
public class VideoAuthController {
    @Autowired
    private IVideoAuthService videoAuthService;
    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("videoAuditOrder")
    public String videoAuthPage(Model model) {
        UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
        if (userInfo.getHasVideoAuth()) {
            model.addAttribute("videoSuccess", true);
            return "videoOrder";
        } else {
            if (userInfo.getVideoAuthId() != null) {
                VideoAuth videoAuth = videoAuthService.get(userInfo.getVideoAuthId());
                model.addAttribute("videoAuth", videoAuth);
                return "videoOrder";
            } else {
                model.addAttribute("auditors", loginInfoService.listAuditors());
                List<Date> orderDates = new ArrayList<>();
                Date date = new Date();
                for (int i = 1; i < 6; i++) {
                    orderDates.add(DateUtil.addDays(date, i));
                }
                model.addAttribute("orderDates", orderDates);
                model.addAttribute("orderTimes", videoAuthService.listOrderTime());
                return "videoOrder";
            }
        }
    }

    @RequestMapping("saveVideoAuthtOrder")
    @ResponseBody
    public JSONResult saveVideoAuthtOrder(VideoAuth to) {
        JSONResult result = null;
        try {
            videoAuthService.order(to);
            result = new JSONResult("登陆成功");
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
