package com.zengzp.product;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/19 15:40
 * @description：启动类
 * @modified By：
 * @version: 1.0$
 */
@EnableRetry
@EnableScheduling
@EnableDubbo
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy=true)
@Import(value = com.learning.code.common.util.IdWorker.class)
@SpringBootApplication
public class ProductInfoApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources (ProductInfoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductInfoApplication.class, args);
    }
}
