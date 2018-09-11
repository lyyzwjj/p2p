package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface BidRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    List<BidRequest> selectAll();

    int updateByPrimaryKey(BidRequest record);

	List<BidRequest> queryDate(BidRequestQueryObject qo);
}