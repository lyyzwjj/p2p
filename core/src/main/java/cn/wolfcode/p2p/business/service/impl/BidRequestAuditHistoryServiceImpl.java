package cn.wolfcode.p2p.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.business.mapper.BidRequestAuditHistoryMapper;
import cn.wolfcode.p2p.business.service.IBidRequestAuditHistoryService;
@Service
public class BidRequestAuditHistoryServiceImpl implements IBidRequestAuditHistoryService {
	@Autowired
	private BidRequestAuditHistoryMapper bhMapper;

	@Override
	public void save(BidRequestAuditHistory bh) {
		bhMapper.insert(bh);

	}

	@Override
	public List<BidRequestAuditHistory> list() {
		return bhMapper.selectAll();
	}

}
