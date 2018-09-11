package cn.wolfcode.p2p.business.mapper;

import java.util.List;

import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;

public interface PaymentScheduleMapper {

    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentSchedule record);

	List<PaymentSchedule> queryData(PaymentScheduleQueryObject qo);
}