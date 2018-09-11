package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.BitStateUtil;
import cn.wolfcode.p2p.base.util.UserContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SystemDictionaryItemMapper dicItemMapper;
    @Override
    public int save(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public int update(UserInfo userInfo) {
        int account =  userInfoMapper.update(userInfo);
        if (account == 0){
            throw new DisplayableException("版本号异常请联系管理员");
        }
        return account;
    }

    @Override
    public UserInfo get(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

	@Override
	public List<SystemDictionaryItem> listBySn(String sn) {
		return dicItemMapper.listBySn(sn);
	}

	@Override
	public void saveBasicInfo(UserInfo to) {
		UserInfo ui = userInfoMapper.selectByPrimaryKey(UserContext.getCurrent().getId());
		ui.addState(BitStateUtil.HAS_BASICINFO);
		ui.setEducationBackground(to.getEducationBackground());
		ui.setIncomeGrade(to.getIncomeGrade());
		ui.setMarriage(to.getMarriage());
		ui.setKidCount(to.getKidCount());
		ui.setHouseCondition(to.getHouseCondition());
		int account = userInfoMapper.update(ui);
		if (account == 0){
            throw new DisplayableException("版本号异常请联系管理员");
        }
	}
}
