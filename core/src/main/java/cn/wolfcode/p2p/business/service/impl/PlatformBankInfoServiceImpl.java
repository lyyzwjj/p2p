package cn.wolfcode.p2p.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.mapper.PlatformBankInfoMapper;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
	@Autowired
	private PlatformBankInfoMapper pimapper;

	@Override
	public void saveOrUpdate(PlatformBankInfo pi) {
		if(pi.getId()!=null) {
			pimapper.updateByPrimaryKey(pi);
		}else {
			pimapper.insert(pi);
		}
	}

	@Override
	public void delete(Long id) {
		pimapper.deleteByPrimaryKey(id);
	}

	@Override
	public PlatformBankInfo get(Long id) {
		return pimapper.selectByPrimaryKey(id);
	}

	@Override
	public List<PlatformBankInfo> list() {
		return pimapper.selectAll();
	}

}
