package com.zengzp.cart;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/30 11:50
 * @description：启动类
 * @modified By：
 * @version: 1$
 */
@SpringBootApplication
// 开启dubbo的自动配置
@EnableDubbo
@Import(value = com.learning.code.common.util.IdWorker.class)
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
