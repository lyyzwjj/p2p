package cn.wolfcode.p2p.mgrsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.base.util.JSONResult;

@Controller
public class SystemDictionaryController {
	@Autowired
	private ISystemDictionaryService systemDictionaryService;

	@RequestMapping("systemDictionary_list")
	public String dicList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
		model.addAttribute("pageInfo", systemDictionaryService.query(qo));
		return "/systemdic/systemDictionary_list";
	}

	@RequestMapping("dic_saveOrUpdate")
	@ResponseBody
	public JSONResult saveOrUpadate(SystemDictionary dic) {
		JSONResult result = null;
		try {
			systemDictionaryService.saveOrUpdate(dic);
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

	@RequestMapping("systemDictionaryItem_update")
	@ResponseBody
	public JSONResult saveOrUpadate(SystemDictionaryItem dicItem) {
		JSONResult result = null;
		try {
			systemDictionaryService.saveOrUpdateItem(dicItem);
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

	@RequestMapping("systemDictionaryItem_list")
	public String dicItemList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
		model.addAttribute("pageInfo", systemDictionaryService.queryDicItems(qo));
		model.addAttribute("systemDictionary_group_detail", systemDictionaryService.list());
		return "/systemdic/systemDictionaryItem_list";
	}
}
