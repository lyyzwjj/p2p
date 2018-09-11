package cn.wolfcode.p2p.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryMapper;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
	@Autowired
	private SystemDictionaryMapper systemDictionaryMapper;
	
	@Autowired
	private SystemDictionaryItemMapper systemDictionaryItemMapper;

	@Override
	public int delete(Long id) {
		return systemDictionaryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SystemDictionary get(Long id) {
		return systemDictionaryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SystemDictionary> list() {
		return systemDictionaryMapper.selectAll();
	}

	@Override
	public PageInfo<SystemDictionary> query(SystemDictionaryQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<SystemDictionary> queryData = systemDictionaryMapper.queryData(qo);
		return new PageInfo<>(queryData);
	}

	@Override
	public PageInfo<SystemDictionaryItem> queryDicItems(SystemDictionaryQueryObject qo) {
		PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
		List<SystemDictionaryItem> queryData = systemDictionaryItemMapper.queryData(qo);
		return new PageInfo<>(queryData);
	}

	@Override
	public void saveOrUpdate(SystemDictionary dic) {
		if(dic.getId() == null) {
			systemDictionaryMapper.insert(dic);
		}else {
			systemDictionaryMapper.updateByPrimaryKey(dic);
		}
	}
	@Override
	public void saveOrUpdateItem(SystemDictionaryItem dicItem) {
		if(dicItem.getId() == null) {
			systemDictionaryItemMapper.insert(dicItem);
		}else {
			systemDictionaryItemMapper.updateByPrimaryKey(dicItem);
		}
	}

}
