package com.zengzp.product.aspect;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.zengzp.product.annotation.NoRepeatSubmit;
import com.zengzp.product.constants.AppCode;
import com.zengzp.product.constants.RedisConstants;
import com.zengzp.product.exception.APIException;
import com.zengzp.product.holder.RequestHolder;
import com.zengzp.product.util.IPUtils;
import com.zengzp.product.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.platform.commons.util.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/25 10:03
 * @description：重复提交切面类
 * @modified By：
 * @version: 1.0$
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {
    @Autowired
    private  RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Pointcut(value = "@annotation(com.zengzp.product.annotation.NoRepeatSubmit)")
    public void pointCut() {
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
            return  handle(point);

    }
    private Object handle(ProceedingJoinPoint point) throws Throwable {
        Signature sig = point.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = IPUtils.getIpAddr(request);
        Object target = point.getTarget();
       Method method=  target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        // 得到类名和方法
        String ipKey = String.format("%s#%s", className, name);
        // 转换成HashCode
        int hashCode = Math.abs(ipKey.hashCode());
        String key = String.format("%s:%s_%d", RedisConstants.AVOID_REPEATABLE_COMMIT, ip, hashCode);
        log.info("ipKey={},hashCode={},key={}", ipKey, hashCode, key);
       NoRepeatSubmit noRepeatSubmit=method.getAnnotation(NoRepeatSubmit.class);
       long lockTime=noRepeatSubmit.lockTime();
        boolean isLock=redissonClient.getLock(key).tryLock(1,lockTime,TimeUnit.MILLISECONDS);
       // String value = (String)redisTemplate.opsForValue().get(key);
        if (!isLock) {
            log.info("请勿重复提交表单");
            return ResultVo.fail(AppCode.REAPEAT_SUBMIT_ERROR);
        }
        // 设置过期时间
        //redisTemplate.opsForValue().set(key, UUID.randomUUID().toString(), lockTime, TimeUnit.MILLISECONDS);
        //执行方法
        Object object;
        try {
            object= point.proceed();
        }finally {
            redissonClient.getLock(key).unlock();
        }

        return object;
    }
}
