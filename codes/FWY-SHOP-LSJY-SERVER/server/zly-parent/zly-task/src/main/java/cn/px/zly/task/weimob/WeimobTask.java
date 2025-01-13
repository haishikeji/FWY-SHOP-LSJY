package cn.px.zly.task.weimob;

import cn.px.zly.module.weimob.service.IWeimobTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Component
public class WeimobTask {
    @Autowired
    private IWeimobTokenService weimobTokenService;

    /**
     * 每小时刷新一次
     */
    @Async
    @Scheduled(cron="0 0 */1 * * ?")
    public void refreshWeimobToken(){
        weimobTokenService.refreshAndPutToken();
        log.warn("refreshToken finish!");
    }
}
