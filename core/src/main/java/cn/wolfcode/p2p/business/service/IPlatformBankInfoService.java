package cn.wolfcode.p2p.business.service;

import java.util.List;

import cn.wolfcode.p2p.business.domain.PlatformBankInfo;

public interface IPlatformBankInfoService {
	void saveOrUpdate(PlatformBankInfo pi);
	void delete(Long id);
	PlatformBankInfo get(Long id);
	List<PlatformBankInfo> list();
}
