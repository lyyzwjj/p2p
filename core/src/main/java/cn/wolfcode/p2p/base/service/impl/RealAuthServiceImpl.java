package cn.wolfcode.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.mapper.RealAuthMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.AssertUtil;
import cn.wolfcode.p2p.base.util.BitStateUtil;
import cn.wolfcode.p2p.base.util.UserContext;
@Service
public class RealAuthServiceImpl implements IRealAuthService {
	@Autowired
	private RealAuthMapper realAuthMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private IUserInfoService userInfoService;

	@Override
	public void saveRealAuth(RealAuth to) {
		UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
		AssertUtil.isTrue(!userInfo.getHasRealAuth(), "用户已通过实名认证");
		AssertUtil.isTrue(userInfo.getRealAuthId() == null, "用户已经在申请中");
		RealAuth ra = new RealAuth();
		ra.setApplier(UserContext.getCurrent());
		ra.setApplyTime(new Date());
		ra.setState(RealAuth.STATE_NORMAL);
		ra.setAddress(to.getAddress());
		ra.setBornDate(to.getBornDate());
		ra.setIdNumber(to.getIdNumber());
		ra.setRealName(to.getRealName());
		ra.setSex(to.getSex());
		ra.setImage1(to.getImage1());
		ra.setImage2(to.getImage2());
		realAuthMapper.insert(ra);
		userInfo.setRealAuthId(ra.getId());
		userInfoMapper.update(userInfo);
	}

	@Override
	public PageInfo<RealAuth> query(RealAuthQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<RealAuth> queryData = realAuthMapper.queryData(qo);
		return new PageInfo<>(queryData);
	}

	@Override
	public void audit(int state, Long id, String remark) {
		RealAuth ra = realAuthMapper.selectByPrimaryKey(id);
		AssertUtil.isNotNull(ra, "参数异常");
		AssertUtil.isTrue(ra.getState() == RealAuth.STATE_NORMAL, "状态异常");	
		// 设置相关属性
		ra.setAuditor(UserContext.getCurrent());
		ra.setAuditTime(new Date());
		ra.setRemark(remark);
		// 如果审核通过
		// 添加用户状态码
		// userInfo设置冗余数据
		// 如果审核失败
		// userInfo删掉 realAuthId
		UserInfo applier = userInfoMapper.selectByPrimaryKey(ra.getApplier().getId());
		if (state == RealAuth.STATE_PASS) {
			ra.setState(RealAuth.STATE_PASS);
			applier.addState(BitStateUtil.HAS_REALAUTH);
			applier.setRealName(ra.getRealName());
			applier.setIdNumber(ra.getIdNumber());
		}else {
			ra.setState(RealAuth.STATE_REJECT);
			applier.setRealAuthId(null);
		}
		realAuthMapper.updateByPrimaryKey(ra);
		userInfoMapper.update(applier);
	}

	@Override
	public RealAuth get(Long realAuthId) {
		return realAuthMapper.selectByPrimaryKey(realAuthId);
	}

}
