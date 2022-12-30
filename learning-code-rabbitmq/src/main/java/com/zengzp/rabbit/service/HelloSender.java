package com.zengzp.rabbit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/7 16:53
 * @description：rabbitmq发送类
 * @modified By：
 * @version: 1$
 */
@Component
@Slf4j
public class HelloSender {
    @Autowired
    private  AmqpTemplate amqpTemplate;

    public void send(){
        log.info("==========服务端开始发送消息============");
        amqpTemplate.convertAndSend("queue.test","hello,rabbit~");
    }

    public static void main(String[] args) {

    }
}
