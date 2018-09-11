package cn.wolfcode.p2p.base.service;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;

public interface IRealAuthService {

	void saveRealAuth(RealAuth to);

	PageInfo<RealAuth> query(RealAuthQueryObject qo);

	void audit(int state, Long id, String remark);

	RealAuth get(Long realAuthId);

}
