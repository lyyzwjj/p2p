package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.Account;

/**
 * Created by WangZhe on 2018/7/23.
 */
public interface IAccountService {
    int save(Account account);
    int update(Account account);
    Account get(Long id);

}
