package cn.wolfcode.p2p.util;

import cn.wolfcode.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by WangZhe on 2018/8/2.
 */
@Component
public class BidRequestPublishCheckJob {
    @Autowired
    private IBidRequestService bidRequestService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void job() {
        this.bidRequestService.publishInTime();
    }
}
