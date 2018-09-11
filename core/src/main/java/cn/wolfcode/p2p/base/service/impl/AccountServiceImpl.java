package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Service
public class AccountServiceImpl implements IAccountService{
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public int save(Account account) {
        return accountMapper.insert(account);
    }

    @Override
    public int update(Account account) {
        return accountMapper.updateByPrimaryKey(account);
    }

    @Override
    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }
}
