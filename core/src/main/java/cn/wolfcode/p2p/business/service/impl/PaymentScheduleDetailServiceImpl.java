package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleDetailMapper;
import cn.wolfcode.p2p.business.service.IPaymentScheduleDetailService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by WangZhe on 2018/7/31.
 */
@Service
@Transactional
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService{
    @Autowired
    private PaymentScheduleDetailMapper psdMapper;

    @Override
    public void save(PaymentScheduleDetail record) {
        psdMapper.insert(record);
    }

    @Override
    public void update(PaymentScheduleDetail record) {
        psdMapper.updateByPrimaryKey(record);
    }

	@Override
	public void batchUpdatePayDate(Date date, Long paymentScheduleId) {
		psdMapper.batchUpdatePayDate(date,paymentScheduleId);
		
	}
}
