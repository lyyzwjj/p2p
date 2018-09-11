package cn.wolfcode.p2p.business.service;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;

/**
 * Created by WangZhe on 2018/7/31.
 */
public interface IPaymentScheduleService {
	void save(PaymentSchedule record);

	void update(PaymentSchedule record);

	PageInfo<PaymentSchedule> query(PaymentScheduleQueryObject qo);

	PaymentSchedule get(Long id);
}
