package com.zengzp.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 15:43
 * @description：启动类
 * @modified By：
 * @version: 1$
 */
@SpringBootApplication
public class CarApplication  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources (CarApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class, args);
    }
}
