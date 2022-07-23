package com.zengzp.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/22 10:27
 * @description：mybatis配置类
 * @modified By：
 * @version: 1.0$
 */
@Configuration
public class MybatisConfig {
    /**
     *      * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     *      
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     *      * 分页插件
     *      
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
