package com.zengzp.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/7 16:49
 * @description：队列配置类
 * @modified By：
 * @version: 1$
 */
@Configuration
public class SenderConf {
    @Bean
    public Queue queue(){
        return new Queue("queue.test");
    }
}
