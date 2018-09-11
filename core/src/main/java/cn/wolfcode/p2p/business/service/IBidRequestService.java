package cn.wolfcode.p2p.business.service;

import java.math.BigDecimal;
import java.util.Date;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.business.domain.BidRequest;
import cn.wolfcode.p2p.business.query.BidRequestQueryObject;

/**
 * Created by WangZhe on 2018/7/27.
 */
public interface IBidRequestService {
	void save(BidRequest bidRequest);

	void update(BidRequest bidRequest);

	void apply(BidRequest to);

	PageInfo<BidRequest> queryInvestList(BidRequestQueryObject qo);

	/**
	 * 发表前审核
	 *
	 * @param id
	 * @param remark
	 * @param state
	 */
	void publishAudit(Long id, String remark, int state,Date publishTime);

	BidRequest get(Long id);

	/**
	 * 用户投标
	 * 
	 * @param amount
	 * @param bidRequestId
	 */
	void bid(BigDecimal amount, Long bidRequestId);

	/**
	 * 风控终审
	 *
	 * @param id
	 * @param remark
	 * @param state
	 */
	void fullAudit1(Long id, String remark, int state);

	/**
	 * 财务终审
	 *
	 * @param id
	 * @param remark
	 * @param state
	 */
    void fullAudit2(Long id, String remark, int state);
    /**
     * 按期还款
     * 
     * @param id 每期还款计划的id
     */
	void returnMoney(Long id);

	/**
	 * 定时发表
	 *
	 */
    void publishInTime();
}
