package com.zengzp.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/30 15:58
 * @description：启动类
 * @modified By：
 * @version: 1$
 */

@SpringBootApplication
public class ElasticsearchJDApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchJDApplication.class,args);
    }
}
