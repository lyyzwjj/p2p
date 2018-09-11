package cn.wolfcode.p2p.base.service.impl;


import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.mapper.OrderTimeMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.mapper.VideoAuthMapper;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.base.util.AssertUtil;
import cn.wolfcode.p2p.base.util.BitStateUtil;
import cn.wolfcode.p2p.base.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by WangZhe on 2018/7/27.
 */
@Service
public class VideoAuthServiceImpl implements IVideoAuthService {
    @Autowired
    private OrderTimeMapper orderTimeMapper;
    @Autowired
    private VideoAuthMapper videoAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<OrderTime> listOrderTime() {
        return orderTimeMapper.selectAll();
    }

    @Override
    public void order(VideoAuth to) {
//        判断 有没有通过视频认证 ,有没有预约成功
//        创建一个视频认证对象,设置相关值
//        保存视频认证对象
//        把视频认证id添加到userInfo 的videoauth
//        修改userInfo
        UserInfo userInfo = userInfoService.get(UserContext.getCurrent().getId());
        AssertUtil.isTrue(!userInfo.getHasVideoAuth(), "用户已通过视频认证");
        AssertUtil.isTrue(userInfo.getVideoAuthId() == null, "用户已经预约了");
        VideoAuth va = new VideoAuth();
        va.setOrderDate(to.getOrderDate());
        va.setOrderTime(to.getOrderTime());
        va.setAuditor(to.getAuditor());
        va.setApplier(UserContext.getCurrent());
        va.setApplyTime(new Date());
        va.setState(VideoAuth.STATE_NORMAL);
        videoAuthMapper.insert(va);
        userInfo.setVideoAuthId(va.getId());
        userInfo.addState(va.getId());
        userInfoMapper.update(userInfo);
    }

    @Override
    public PageInfo<VideoAuth> query(VideoAuthQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<VideoAuth> list = videoAuthMapper.queryData(qo);
        return new PageInfo<>(list);
    }

    @Override
    public void audit(Long id, int state, String remark) {
        VideoAuth va = videoAuthMapper.selectByPrimaryKey(id);
        AssertUtil.isNotNull(va, "参数异常");
        AssertUtil.isTrue(va.getState() == VideoAuth.STATE_NORMAL, "状态异常");
        va.setRemark(remark);
        va.setAuditTime(new Date());
        UserInfo applier = userInfoMapper.selectByPrimaryKey(va.getApplier().getId());
        if (state == VideoAuth.STATE_PASS) {
            va.setState(VideoAuth.STATE_PASS);
            applier.addState(BitStateUtil.HAS_VIDEOAUTH);
        }else {
            va.setState(VideoAuth.STATE_REJECT);
            applier.setVideoAuthId(null);
        }
        videoAuthMapper.updateByPrimaryKey(va);
        userInfoMapper.update(applier);
    }

    /**
     * 根据id查询VideoAuth
     * @param videoAuthId
     * @return
     */
    @Override
    public VideoAuth get(Long videoAuthId) {
        return videoAuthMapper.selectByPrimaryKey(videoAuthId);
    }
}
