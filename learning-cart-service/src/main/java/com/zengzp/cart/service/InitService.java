package com.zengzp.cart.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.entity.Preferential;
import com.zengzp.cart.entity.Sku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/1/3 10:50
 * @description：初始化商品库
 * @modified By：
 * @version: 1$
 */
@Component
@Slf4j
public class InitService implements InitializingBean {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void afterPropertiesSet() throws Exception {
       /* preferentialToRedis();*/
    }

    /**
     * 缓存sku到redis
     */
    private void skuToRedis(){
        log.info("===开始预热缓存===");
        Sku sku=null;
        for (int i=1;i<=10;i++){
            sku=new Sku();
            sku.setId(i);
            sku.setAlertNum(100);
            sku.setBrandName("华为");
            sku.setCategoryId(i);
            sku.setCategoryName("手机");
            sku.setCreateDate(new Date());
            sku.setImage("=====image url=======");
            sku.setImages("=====images url=======");
            sku.setName("meta 40 pro--->"+i);
            sku.setNum(100);
            sku.setPrice(4900.00);
            sku.setSn(RandomUtil.randomNumbers(10));
            if(i%2==0) {
                sku.setSpec("5G 黑色 256G");
            }else {
                sku.setSpec("5G 白色 256G");
            }
            sku.setSpuId(i);
            sku.setStatus("1");
            redisTemplate.boundHashOps(CacheKey.SKU_LIST.toString()).put(sku.getId(),sku);
        }

        log.info("====缓存商品完成=====");
    }

    /**
     * 将优惠规则初始化到Redis
     */
    private void preferentialToRedis(){
        log.info("===开始预热优惠规则至缓存===");
        Preferential preferential=null;
        for (int i=1;i<=1;i++){
            preferential=new Preferential();
            preferential.setBuyMoney(300.00);
            preferential.setPreMoney(30.00);
            preferential.setCategoryId(3L);
            preferential.setId((long)i);
            preferential.setType("1");
            preferential.setStatus("0");
            preferential.setStartTime(DateUtil.parse("2023-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
            preferential.setEndTime(DateUtil.parse("2023-01-12 23:59:59","yyyy-MM-dd HH:mm:ss"));
            redisTemplate.boundSetOps(CacheKey.PRE_LIST.toString()).add(preferential) ;
        }
        log.info("===预热优惠规则缓存结束===");
    }
}
