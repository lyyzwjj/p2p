package cn.wolfcode.p2p.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.JSONResult;
import cn.wolfcode.p2p.base.util.UploadUtil;
import cn.wolfcode.p2p.base.util.UserContext;

/**
 * 前台实名认证控制
 * 
 * @author WangZhe
 *
 */
@Controller
public class RealAuthController {
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private IUserInfoService userInfoService;
	@Value("${app.imgfolder}")
	private String imgFolder;
	@RequestMapping("realAuth")
	public String realAuthPage(Model model) {
		UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
		if (userInfo.getHasRealAuth()) {
			model.addAttribute("auditing", false);
			model.addAttribute("realAuth",realAuthService.get(userInfo.getRealAuthId()));
			return "/realAuth_result";
		}else {
			if (userInfo.getRealAuthId() == null) {
				return "/realAuth";
			}else {
				model.addAttribute("auditing", true);
				return "/realAuth_result";
			}
		}
	}
	@RequestMapping("realAuth_save")
	@ResponseBody
	public JSONResult saveRealAuth(RealAuth to) {
		JSONResult result = null;
		try {
			realAuthService.saveRealAuth(to);
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
	@RequestMapping("uploadImg")
	@ResponseBody
	public String uploadImg(MultipartFile file) {
		return "/upload/"+ UploadUtil.upload(file, imgFolder);
	}
}
