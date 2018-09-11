package cn.wolfcode.p2p.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.BitStateUtil;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.MoneyWithdraw;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.mapper.MoneyWithdrawMapper;
import cn.wolfcode.p2p.business.query.MoneyWithdrawQueryObject;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.IMoneyWithdrawService;
import cn.wolfcode.p2p.business.service.ISystemAccountFlowService;
import cn.wolfcode.p2p.business.service.ISystemAccountService;

@Service
@Transactional
public class MoneyWithdrawServiceImpl implements IMoneyWithdrawService {
	@Autowired
	private MoneyWithdrawMapper mwMapper;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountFlowService accountFlowService;
	@Autowired
	private ISystemAccountFlowService systemAccountFlowService;
	@Autowired
	private ISystemAccountService systemAccountService;

	@Override
	public void audit(Long id, int state, String remark) {
		MoneyWithdraw mw = mwMapper.selectByPrimaryKey(id);
		UserInfo applier = userInfoService.get(mw.getApplier().getId());
		Account account = accountService.get(mw.getApplier().getId());

		// 判断处于待审核状态
		if (mw != null && mw.getState() == MoneyWithdraw.STATE_NORMAL) {
			// 设置审核相关属性
			mw.setAuditor(UserContext.getCurrent());
			mw.setAuditTime(new Date());
			mw.setRemark(remark);
			mw.setState(state);
			// 去掉状态码
			applier.removeState(BitStateUtil.HAS_MONEY_WITHDRWA_IN_PROCESS);
			userInfoService.update(applier);
			// 如果审核通过
			if (state == MoneyWithdraw.STATE_PASS) {
				// 冻结金额减少,生成提现成功流水(实际提现的流水)
				account.setFreezedAmount(account.getFreezedAmount().subtract(mw.getMoneyAmount()));
				accountFlowService.createMoneyWithdrawFlowSuccessFlow(account,mw);
				// 冻结金额减少,生成支付提现手续费流水
				accountFlowService.createMoneyWithdrawFeeFlow(account,mw);
				// 系统收取提现手续费 生成系统流水
				SystemAccount systemAccount = systemAccountService.selectCurrent();
				systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(mw.getFee()));
				systemAccountService.update(systemAccount);
				systemAccountFlowService.createMoneyWithdrawFeeFlow(systemAccount,mw);
			}else {
				// 如果审核拒绝
				// 取消冻结金额
				account.setFreezedAmount(account.getFreezedAmount().subtract(mw.getMoneyAmount()));
				// 生成提现失败流水
				accountFlowService.createMoneyWithdrawFlowFailedFlow(account,mw);
			}
			mwMapper.updateByPrimaryKey(mw);
			accountService.update(account);
		}
	}

	@Override
	public PageInfo<MoneyWithdraw> query(MoneyWithdrawQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<MoneyWithdraw> list = mwMapper.queryData(qo);
		return new PageInfo<>(list);
	}

	@Override
	public void save(MoneyWithdraw record) {
		mwMapper.insert(record);
	}

}
