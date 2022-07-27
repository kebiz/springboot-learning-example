package com.zengzp.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/19 15:40
 * @description：启动类
 * @modified By：
 * @version: 1.0$
 */
@EnableRetry
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
