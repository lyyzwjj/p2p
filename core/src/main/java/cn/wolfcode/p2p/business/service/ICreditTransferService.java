package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.CreditTransfer;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by WangZhe on 2018/8/3.
 */
public interface ICreditTransferService {
    void update(CreditTransfer creditTransfer);

    /**
     * 查看自己可以转让的标
     *
     * @param qo
     * @return
     */
    PageInfo<CreditTransfer> listCanTransferCredit(CreditTransferQueryObject qo);

    /**
     * 转让标
     *
     * @param bidId
     */
    void creditTransfer(Long[] bidId);

    /**
     *查询所有待转让的标
     *
     * @param cto
     * @return
     */
    PageInfo<CreditTransfer> query(CreditTransferQueryObject cto);

    /**
     * 债权标的认购
     *
     * @param id
     */
    void subscribe(Long id);

    /**
     * 根据还款计划明细的bidId找到转让标对象
     *
     * @param bidId
     */
    CreditTransfer findNeedCancel(Long bidId);

    /**
     * 还款人还款时候 如果还款明细有对应的认购标在认购中 则取消认购标
     *
     * @param needCancel
     */
    void cancel(CreditTransfer needCancel);
}
