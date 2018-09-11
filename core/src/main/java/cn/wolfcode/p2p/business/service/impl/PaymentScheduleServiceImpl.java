package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleMapper;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.business.service.IPaymentScheduleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by WangZhe on 2018/7/31.
 */
@Service
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {
    @Autowired
    private PaymentScheduleMapper psMapper;
    @Override
    public void save(PaymentSchedule record) {
        psMapper.insert(record);
    }

    @Override
    public void update(PaymentSchedule record) {
        psMapper.updateByPrimaryKey(record);
    }

	@Override
	public PageInfo<PaymentSchedule> query(PaymentScheduleQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<PaymentSchedule> list =  psMapper.queryData(qo);
		return new PageInfo<>(list);
	}

	@Override
	public PaymentSchedule get(Long id) {
		return psMapper.selectByPrimaryKey(id);
	}
	
}
