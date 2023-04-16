package com.zengzp.product.config;

import com.learning.code.common.model.MessageSendLog;
import com.learning.dubbo.MessageSendLogService;
import com.zengzp.product.service.OrderService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/7 11:12
 * @description：定时器配置类
 * @modified By：
 * @version: 1.0$
 */
@Data
@Component
@Slf4j
@PropertySource("classpath:/taskConfig.ini")
public class ScheduleConfig implements SchedulingConfigurer {
    @Value("${printTime.cron}")
    private String cron;

    private long timer;
    @Autowired
    private OrderService orderService;
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //log.info("====定时任务开始重发===");
                //orderService.doMessageSendLog();

            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 使用CronTrigger触发器，可动态修改cron表达式来操作循环规则
                CronTrigger cronTrigger = new CronTrigger(cron);

                // 使用不同的触发器，为设置循环时间的关键，区别于CronTrigger触发器，该触发器可随意设置循环间隔时间，单位为毫秒
               // PeriodicTrigger periodicTrigger = new PeriodicTrigger(timer);
                return cronTrigger.nextExecutionTime(triggerContext);
            }
        });
    }
}
