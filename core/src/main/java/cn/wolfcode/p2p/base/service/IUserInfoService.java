package cn.wolfcode.p2p.base.service;

import java.util.List;

import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.domain.UserInfo;

/**
 * Created by WangZhe on 2018/7/23.
 */
public interface IUserInfoService {
    int save(UserInfo userInfo);
    int update(UserInfo userInfo);
    UserInfo get(Long id);
	/**
	 * 根据数据字典sn查询对应字典明细集合
	 * @param sn
	 * @return
	 */
	List<SystemDictionaryItem> listBySn(String sn);
	/**
	 * 改变前台用户基本资料
	 * @param to
	 */
	void saveBasicInfo(UserInfo to);
}
