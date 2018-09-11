package cn.wolfcode.p2p.business.service;


import java.util.List;

import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;

public interface IBidRequestAuditHistoryService {
	void save(BidRequestAuditHistory bh);
	List<BidRequestAuditHistory> list();
}
