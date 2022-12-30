package com.zengzp.rabbit.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/8 10:29
 * @description：ApplacationContext工具类
 * @modified By：
 * @version: 1$
 */
@Component
@Slf4j
public class ContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(context ==null) {
            context = applicationContext;
        }
        log.info("applicationContext初始化成功");
    }
    public static ApplicationContext getApplicationContext(){
        return context;
    }
    public static Object getBean(String beanName){
       return context.getBean(beanName);
    }
}
