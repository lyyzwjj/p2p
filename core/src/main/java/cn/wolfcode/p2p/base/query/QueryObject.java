package cn.wolfcode.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WangZhe on 2018/7/23.
 */
@Getter @Setter
public class QueryObject {
    private Integer currentPage = 1;
    private Integer pageSize = 5;
}
