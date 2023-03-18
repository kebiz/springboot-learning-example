package com.zengzp.cart.service.impl;

import cn.hutool.json.JSONUtil;
import com.learning.code.common.model.Order;
import com.zengzp.cart.CartApplication;
import com.zengzp.cart.contant.CacheKey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApplication.class)
public class OrderTestServiceImplTest {
    @Autowired
    OrderTestServiceImpl orderServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() throws Exception {
        //System.out.println(stockService.getStock("sku-key-12"));
      Order order=new Order();
        order.setUserName("userName");
        System.out.println(JSONUtil.toJsonStr(orderServiceImpl.preCheckOrder(order)));
    }
    @Test
    public void testGet() throws Exception {
        System.out.println(JSONUtil.toJsonStr(orderServiceImpl.getOrderListByuserName("userName")));
    }
    @Test
    public void testDelete() throws Exception {
        redisTemplate.boundHashOps(CacheKey.ORDER_LIST.toString()).delete("userName");
    }

    @Test
    public void testGetInventoryFlow() throws Exception {
        Set<Map.Entry<Object, Object>> sku_key_121 = redissonClient.getMap("inventory_flow_1628229916826267648").entrySet();
        sku_key_121.forEach(e->{
            System.out.println(e.getKey());
            System.out.println(e.getValue());
        });

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme