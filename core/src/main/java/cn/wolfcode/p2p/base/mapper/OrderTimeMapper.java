package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.OrderTime;
import java.util.List;

public interface OrderTimeMapper {

    OrderTime selectByPrimaryKey(Long id);

    List<OrderTime> selectAll();

}