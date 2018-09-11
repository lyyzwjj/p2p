package cn.wolfcode.p2p.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.util.AssertUtil;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.mapper.RechargeOfflineMapper;
import cn.wolfcode.p2p.business.query.RechargeOfflineQueryObject;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.IRechargeOfflineService;
@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
	@Autowired
	private RechargeOfflineMapper rlMapper;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountFlowService accountFlowService;
	@Override
	public void save(RechargeOffline rl) {
		rlMapper.insert(rl);
	}

	@Override
	public void update(RechargeOffline rl) {
		rlMapper.updateByPrimaryKey(rl);

	}

	@Override
	public void saveRecharge(RechargeOffline to) {
		RechargeOffline ro = new RechargeOffline();
		ro.setAmount(to.getAmount());
		ro.setApplier(UserContext.getCurrent());
		ro.setApplyTime(new Date());
		ro.setBankInfo(to.getBankInfo());
		ro.setNote(to.getNote());
		ro.setRechargeDate(to.getRechargeDate());
		ro.setTradeCode(to.getTradeCode());
		ro.setState(RechargeOffline.STATE_NORMAL);
		rlMapper.insert(ro);
	}

	@Override
	public PageInfo<RechargeOffline> queryData(RechargeOfflineQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<RechargeOffline> list = rlMapper.queryData(qo);
		return new PageInfo<>(list);
	}

	@Override
	public void audit(Long id, int state, String remark) {
		// 判断是否处于待审核状态
		// 设置相关审核属性
		// 如果审核通过
		// 用户账户可用余额增加
		// 修改
		RechargeOffline ro = rlMapper.selectByPrimaryKey(id);
		AssertUtil.isNotNull(ro, "参数异常");
		if (ro.getState() == RechargeOffline.STATE_NORMAL) {
			ro.setAuditor(UserContext.getCurrent());
			ro.setAuditTime(new Date());
			ro.setRemark(remark);
			ro.setState(state);
			if (state == RechargeOffline.STATE_PASS) {
				Account account = accountService.get(ro.getApplier().getId());
				account.setUsableAmount(account.getUsableAmount().add(ro.getAmount()));
				accountService.update(account);
				accountFlowService.creatRechargeOfflineFlow(account,ro);
			}
			rlMapper.updateByPrimaryKey(ro);
		}
		
	}


}
