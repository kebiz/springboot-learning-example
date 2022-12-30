package com.zengzp.rabbit.service;

import com.zengzp.rabbit.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/7 16:59
 * @description：rabbitmq接收类
 * @modified By：
 * @version: 1$
 */
@Component
@Slf4j
public class HelloService {
    @RabbitListener(queues = "queue.test")
    public void receive(String str){
        log.info("======接收到的信息=========="+str);
    }
}
