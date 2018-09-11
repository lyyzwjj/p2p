package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.base.util.BitStateUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Getter
@Setter
public class UserInfo extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private int version;// 乐观锁
	private Long bitState = 0L;// 用户状态码
	private String realName;// 真实姓名
	private String idNumber;// 身份证号码
	private String phoneNumber;// 手机号码
	private String email;// 邮箱
	private SystemDictionaryItem incomeGrade;// 收入情况
	private SystemDictionaryItem marriage;// 婚姻情况
	private SystemDictionaryItem kidCount; // 子女情况
	private SystemDictionaryItem educationBackground;// 学历情况
	private SystemDictionaryItem houseCondition;// 住房情况
	private Long realAuthId;
	private Long videoAuthId;

	public boolean getHasBasicInfo() {
		return BitStateUtil.hasState(BitStateUtil.HAS_BASICINFO, bitState);
	}

	public boolean getHasRealAuth() {
		return BitStateUtil.hasState(BitStateUtil.HAS_REALAUTH, bitState);
	}

	public boolean getHasVideoAuth() {
		return BitStateUtil.hasState(BitStateUtil.HAS_VIDEOAUTH, bitState);
	}

	public boolean getHasBidRequestInProcess() {
		return BitStateUtil.hasState(BitStateUtil.HAS_BIDREQUEST_PROCESS, bitState);
	}
	public boolean getHasBindBank() {
		return BitStateUtil.hasState(BitStateUtil.HAS_BINK_BANK, bitState);
	}
	public boolean getHasMoneyWithdrawInProcess() {
		return BitStateUtil.hasState(BitStateUtil.HAS_MONEY_WITHDRWA_IN_PROCESS, bitState);
	}

	public void addState(Long state) {
		this.bitState = BitStateUtil.addState(this.bitState, state);
	}

	public void removeState(Long state) {
		this.bitState = BitStateUtil.removeState(this.bitState, state);
	}
}
