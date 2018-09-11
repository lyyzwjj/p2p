package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);

	List<SystemDictionaryItem> queryData(SystemDictionaryQueryObject qo);
	
	/**
	 * 根据数据字典的sn查找数据字典明细集合
	 * @param sn
	 * @return
	 */
	List<SystemDictionaryItem> listBySn(String sn);
}