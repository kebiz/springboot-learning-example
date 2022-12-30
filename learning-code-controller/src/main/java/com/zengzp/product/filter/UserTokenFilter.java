package com.zengzp.product.filter;

import cn.hutool.core.lang.Assert;
import com.zengzp.product.entity.TokenInfo;
import com.zengzp.product.util.ApiUtil;
import com.zengzp.product.util.MD5Util;
import me.zhyd.houtu.util.StringUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/10 16:07
 * @description：验证用户请求参数过滤器
 * @modified By：
 * @version: 1$
 */
public class UserTokenFilter extends FormAuthenticationFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req=(HttpServletRequest)request;
        String token = req.getHeader("token");
        String timestamp = req.getHeader("timestamp");
        // 随机字符串
        String nonce = req.getHeader("nonce");
        String sign = req.getHeader("sign");
        Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");

        // 获取超时时间
        //NotRepeatSubmit notRepeatSubmit = ApiUtil.getNotRepeatSubmit(handler);
       // long expireTime = notRepeatSubmit == null ? 5 * 60 * 1000 : notRepeatSubmit.value();
        long expireTime =5 * 60 * 1000;
        // 2\. 请求时间间隔
        long reqeustInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
        Assert.isTrue(reqeustInterval < expireTime, "请求超时，请重新请求");

        // 3\. 校验Token是否存在
        ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
        TokenInfo tokenInfo = tokenRedis.get(token);
        Assert.notNull(tokenInfo, "token错误");

        // 4\. 校验签名(将所有的参数加进来，防止别人篡改参数) 所有参数看参数名升续排序拼接成url
        // 请求参数 + token + timestamp + nonce
        String signString = ApiUtil.concatSignString(req) + tokenInfo.getAppInfo().getKey() + token + timestamp + nonce;
        String signature = MD5Util.encode(signString);
        boolean flag = signature.equals(sign);
        Assert.isTrue(flag, "签名错误");

        // 5\. 拒绝重复调用(第一次访问时存储，过期时间和请求超时时间保持一致), 只有标注不允许重复提交注解的才会校验
       /* if (notRepeatSubmit != null) {
            ValueOperations<String, Integer> signRedis = redisTemplate.opsForValue();
            boolean exists = redisTemplate.hasKey(sign);
            Assert.isTrue(!exists, "请勿重复提交");
            signRedis.set(sign, 0, expireTime, TimeUnit.MILLISECONDS);
        }*/

        return super.onPreHandle(request,response,mappedValue);
    }
}
