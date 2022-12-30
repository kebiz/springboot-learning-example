package com.zengzp.product.controller;

import com.zengzp.product.config.ScheduleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/7 11:51
 * @description：定时任务测试类
 * @modified By：
 * @version: 1.0$
 */
@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleConfig scheduleConfig;

    @Autowired
    public ScheduleController(ScheduleConfig scheduleConfig){
        this.scheduleConfig=scheduleConfig;
    }
    @GetMapping("updateCron")
    public  String updateCron(String cron){
        log.info("new cron {}",cron);
        scheduleConfig.setCron(cron);
        return "ok";
    }
}
