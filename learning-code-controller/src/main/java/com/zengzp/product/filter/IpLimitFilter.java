package com.zengzp.product.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/15 11:23
 * @description：ip限流配置类
 * @modified By：
 * @version: 1$
 */
/*@Slf4j
@RefreshScope
@Component
public class IpLimitFilter implements GlobalFilter, Ordered {

    @Resource
    RedisTemplate redisTemplate;

    // 限流时间(单位：秒)
    @Value(value = "${IpLimiter.expireTime:1}")
    private Integer expireTime;

    // 限流次数
    @Value(value = "${IpLimiter.limitTimes:100}")
    private Integer limitTimes;

    *//**
     * getRedisScript 读取脚本工具类
     * 这里设置为Long,是因为ipLimiter.lua 脚本返回的是数字类型
     *//*
    private DefaultRedisScript<Long> getRedisScript;

    private static String SIGN = ":";

    private static String IP_LIMIT_FILTER = "IP_LIMIT_FILTER:";

    // ip请求黑名单
    private static String IP_LIMIT_BLACK_LIST = "IP_LIMIT_BLACK_LIST:";

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("ipLimiter.lua")));
        log.info("IpLimitHandler[分布式限流处理器]脚本加载完成");
    }


    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.debug("IpLimitFilter[分布式限流处理器]开始执行限流操作");

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String limitIp = IpUtil.getIpAddr(request);
        //获取请求路径
        String url = request.getPath().toString();
        log.info("访问IP为:{}, 访问的地址: {}", limitIp, url);

        *//**
         * 执行Lua脚本
         *//*
        List<String> ipList = new ArrayList();
        // 设置key值为注解中的值
        String key = IP_LIMIT_FILTER.concat(url).concat(SIGN).concat(limitIp);
        ipList.add(key);

        *//**
         * 调用脚本并执行
         *//*
        Long result = (Long) redisTemplate.execute(getRedisScript, ipList, expireTime, limitTimes);
        if (result == 0) {
            // TODO 放进全局黑名单【用于后续观察】
            redisTemplate.opsForValue().set(IP_LIMIT_BLACK_LIST + limitIp, 1, 7, TimeUnit.DAYS);

            String msg = "由于超过单位时间=" + expireTime + "-允许的请求次数=" + limitTimes + "[触发限流]";
            log.info(msg);
            // 达到限流返回给前端信息
            String errMessage = "{\"code\":\"error\",\"message\":\"请求过于频繁，请稍后再试\"}";
            byte[] bits = errMessage.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            //指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        } else {
            // 直接放行
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}*/
