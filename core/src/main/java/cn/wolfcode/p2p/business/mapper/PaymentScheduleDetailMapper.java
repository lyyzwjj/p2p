package cn.wolfcode.p2p.business.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;

public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentScheduleDetail record);

	void batchUpdatePayDate(@Param("payDate")Date date, @Param("paymentScheduleId")Long paymentScheduleId);

    void changePaymentScheduleDetailTransState(@Param("bidId") Long bidId, @Param("state") int state);

    /**
     * 认购债权转让标批量修改投资人id
     *
     * @param id
     * @param bidId
     */
    void batchUpdateInvestor(@Param("investorId") Long id, @Param("bidId") Long bidId);
}