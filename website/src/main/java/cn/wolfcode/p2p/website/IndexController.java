package cn.wolfcode.p2p.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.util.Constants;
import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;
import cn.wolfcode.p2p.business.service.IBidRequestService;

@Controller
public class IndexController {
	@Autowired
	private IBidRequestService bidRequestService;
	@RequestMapping("main")
	public String main(Model model){
		BidRequestQueryObject qo = new BidRequestQueryObject();
		qo.setOrderBy("bidRequestState");
		qo.setOrderType("asc");
		qo.setStates(new int[]{Constants.BIDREQUEST_STATE_BIDDING,Constants.BIDREQUEST_STATE_PAYING_BACK,Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
		model.addAttribute("bidRequests", bidRequestService.queryInvestList(qo).getList());
		qo.setStates(null);
		qo.setState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
		model.addAttribute("publishPendngBidRequests",bidRequestService.queryInvestList(qo).getList());
		return "main";
	}
}
