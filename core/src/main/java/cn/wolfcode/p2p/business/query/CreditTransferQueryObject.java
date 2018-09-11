package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/8/3.
 */
@Getter
@Setter
public class CreditTransferQueryObject extends QueryObject{
    private int state;
}
