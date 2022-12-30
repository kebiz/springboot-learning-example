package com.zengzp.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/14 11:41
 * @description：线程配置类
 * @modified By：
 * @version: 1$
 */
@Configuration
@EnableAsync //开启异步请求
public class ThreadPoolConfig {

    @Resource
    private Environment env;

    //创建线程池
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix("--------------全局线程池-----------------");
       // pool.setCorePoolSize(Integer.parseInt(env.getProperty("threadpool.corePoolSize")));
       // pool.setMaxPoolSize(Integer.parseInt(env.getProperty("threadpool.maxPoolSize")));
       // pool.setKeepAliveSeconds(Integer.parseInt(env.getProperty("threadpool.queueCapacity")));
        //pool.setQueueCapacity(Integer.parseInt(env.getProperty("threadpool.keepAliveSeconds")));
        // 直接在execute方法的调用线程中运行
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        pool.initialize();
        return pool;
    }
}
