package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.Bid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BidMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    List<Bid> selectAll();

    int updateByPrimaryKey(Bid record);

    void updateStates(@Param("bidRequestId") Long bidRequestId, @Param("bidRequestState")int bidRequestState);

    /**
     * 根据招标对象的id找到投标对象列表
     *
     * @param bidRequestId
     * @return
     */
    List<Bid> listByBidRequestId(Long bidRequestId);
}