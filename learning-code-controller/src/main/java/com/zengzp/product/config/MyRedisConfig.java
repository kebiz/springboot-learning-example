package com.zengzp.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/22 15:44
 * @description：redis配置类
 * @modified By：
 * @version: 1.0$
 */
@Configuration
public class MyRedisConfig extends RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
   // @Value("${redis.password}")
   // private String password;
    @Bean
    public RedissonClient createRedisson(){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port);
       return Redisson.create(config);
    }
}
