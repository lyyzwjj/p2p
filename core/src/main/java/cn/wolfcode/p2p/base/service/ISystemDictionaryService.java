package cn.wolfcode.p2p.base.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;

public interface ISystemDictionaryService {
	int delete(Long id);
	SystemDictionary get(Long id);
	List<SystemDictionary> list();
	PageInfo<SystemDictionary> query(SystemDictionaryQueryObject qo);
	PageInfo<SystemDictionaryItem> queryDicItems(SystemDictionaryQueryObject qo);
	void saveOrUpdateItem(SystemDictionaryItem dicItem);
	void saveOrUpdate(SystemDictionary dic);
}
