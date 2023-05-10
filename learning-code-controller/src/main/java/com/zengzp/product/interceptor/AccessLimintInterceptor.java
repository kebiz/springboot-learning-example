package com.zengzp.product.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.zengzp.product.annotation.NoRepeatSubmit;
import com.zengzp.product.constants.AppCode;
import com.zengzp.product.constants.RedisConstants;
import com.zengzp.product.exception.BaseException;
import com.zengzp.product.exception.RepeatSubmitException;
import com.zengzp.product.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/5/10 14:54
 * @description：请求处理拦截类
 * @modified By：
 * @version: 1$
 */
@Component
@Slf4j
public class AccessLimintInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if(handler instanceof HandlerMethod){
           HandlerMethod handlerMethod=(HandlerMethod)handler;
           // 获取目标接口方法所在类的注解@AccessLimit
           NoRepeatSubmit targetClassAnnotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(NoRepeatSubmit.class);
            // 定义标记位，标记此类是否加了@AccessLimit注解
           boolean isBrushForAllInterface = false;
           String ip = IPUtils.getIpAddr(request);
           String uri = request.getRequestURI();
           long lockTime=0L;
           long  maxTime=0L;
           long forbiddenTime=0L;
           // 转换成HashCode
           int hashCode = Math.abs(uri.hashCode());
           String lock_key = String.format("%s:%s_%d", RedisConstants.AVOID_REPEATABLE_COMMIT, ip, hashCode);
           String count_key = String.format("%s:%s_%d", RedisConstants.AVOID_REPEATABLE_COUNT, ip, hashCode);
           log.info("ipKey={},uri={},key={}", ip, uri, lock_key);
           if(!ObjectUtil.isNull(targetClassAnnotation)){
                lockTime=targetClassAnnotation.lockTime();
                maxTime = targetClassAnnotation.maxTime();
                forbiddenTime = targetClassAnnotation.forbiddenTime();
               isBrushForAllInterface=true;

           }
           NoRepeatSubmit noRepeatSubmit = handlerMethod.getMethodAnnotation(NoRepeatSubmit.class);
           if(!ObjectUtil.isNull(noRepeatSubmit)) {
               lockTime = noRepeatSubmit.lockTime();
               maxTime = noRepeatSubmit.maxTime();
               forbiddenTime = noRepeatSubmit.forbiddenTime();
               if(isForbidden(lock_key,count_key,lockTime,maxTime,forbiddenTime)){
                   throw  new RepeatSubmitException();
               }
           }else {
               if(isBrushForAllInterface && isForbidden(lock_key,count_key,lockTime,maxTime,forbiddenTime)){
                   throw  new RepeatSubmitException();
               }
           }
       }

        return true;
    }

    /**
     * 判断是否重复提交
     * @param lock_key
     * @param count_key
     * @param lockTime
     * @param maxTime
     * @param forbiddenTime
     * @return
     */
    private boolean isForbidden(String lock_key,String count_key,long lockTime,long maxTime,long forbiddenTime){
        //1、判断是否被禁用
        Object isLock = redisTemplate.opsForValue().get(lock_key);
        if(ObjectUtil.isNull(isLock)){
            //为空说明还没被禁用
            Object count = redisTemplate.opsForValue().get(count_key);
            if(ObjectUtil.isNull(count)){
                //第一次进入+1
                redisTemplate.opsForValue().set(count_key,1,lockTime, TimeUnit.MILLISECONDS);
            }else {
                if((Long)count<maxTime){
                    //累计加1
                    redisTemplate.opsForValue().increment(count_key);
                }else {
                    //禁用
                    redisTemplate.opsForValue().set(lock_key,1,forbiddenTime,TimeUnit.MILLISECONDS);
                    //删除统计数
                    redisTemplate.delete(count_key);
                    return  true;
                }
            }
        }else {
            //禁用
            return  true;
        }
        return false;
    }
}
