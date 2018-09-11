package cn.wolfcode.p2p.base.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.wolfcode.p2p.base.domain.OrderTime;
import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;

/**
 * Created by WangZhe on 2018/7/27.
 */
public interface IVideoAuthService {
    List<OrderTime> listOrderTime();

    void order(VideoAuth to);

    PageInfo<VideoAuth> query(VideoAuthQueryObject qo);

    void audit(Long id, int state, String remark);

    VideoAuth get(Long videoAuthId);
}
