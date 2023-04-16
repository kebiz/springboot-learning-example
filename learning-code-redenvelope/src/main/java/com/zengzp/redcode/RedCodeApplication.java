package com.zengzp.redcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author zengzp
 * @Date 2023/3/19 15:13
 * @Version 1.0
 **/
@MapperScan("com.zengzp.redcode.dao")
@SpringBootApplication
public class RedCodeApplication {
    public static  void main(String[] args){
        SpringApplication.run(RedCodeApplication.class,args);

    }
}
