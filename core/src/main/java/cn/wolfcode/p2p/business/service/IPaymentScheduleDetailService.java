package cn.wolfcode.p2p.business.service;

import java.util.Date;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import org.apache.ibatis.annotations.Param;

/**
 * Created by WangZhe on 2018/7/31.
 */
public interface IPaymentScheduleDetailService {
    void save(PaymentScheduleDetail record);
    void update(PaymentScheduleDetail record);
	void batchUpdatePayDate(Date date, Long paymentScheduleId);
}
