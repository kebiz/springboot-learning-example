package com.zengzp.rabbit;

import com.zengzp.rabbit.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/7 16:42
 * @description：rabbitmq启动类
 * @modified By：
 * @version: 1$
 */
@SpringBootApplication
@Slf4j
public class RabbitApplication implements ApplicationContextAware {
    private static ApplicationContext context;
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class,args);

    }
    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}
