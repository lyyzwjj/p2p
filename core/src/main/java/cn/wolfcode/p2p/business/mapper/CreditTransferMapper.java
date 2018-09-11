package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.CreditTransfer;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CreditTransferMapper {

    int insert(CreditTransfer record);

    CreditTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKey(CreditTransfer record);

    List<CreditTransfer> ListCanTransferCredit(CreditTransferQueryObject qo,@Param("userId") Long id);

    List<CreditTransfer>  ListReadyTransferCredit(@Param("userId") Long id, @Param("bidId") Long[] bidId);

    List<CreditTransfer> queryData(CreditTransferQueryObject cto);


    CreditTransfer findNeedCancel(Long bidId);

    void changePaymentScheduleDetailTransState(@Param("bidId") Long bidId, @Param("intrans") int intrans);
}
