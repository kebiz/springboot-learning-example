package com.zengzp.cart.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.zengzp.cart.CartApplication;
import com.zengzp.cart.contant.CacheKey;
import com.zengzp.cart.entity.Preferential;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApplication.class)
public class CartServiceImplTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CartServiceImpl cartServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindCartList() throws Exception {
        List<Map<String, Object>> result = cartServiceImpl.findCartList("userName");
        System.out.println(JSONUtil.toJsonStr(result));
       /* Assert.assertEquals(Arrays.<Map<String, Object>>asList(new HashMap<String, Object>() {{
            put("String", "Map");
        }}), result);*/
    }

    @Test
    public void testAddCart() {
        // Setup
        // Run the test
        final boolean result = cartServiceImpl.addCart("userName", 4L, 10);

        // Verify the results
        assertTrue(result);
    }

    @Test
    public void testDeleteCartItem() {
        // Setup
        // Run the test
        cartServiceImpl.deleteCartItem("userName");

        // Verify the results
    }

    @Test
    public void testUpdateChecked() {
        // Setup
        // Run the test
        cartServiceImpl.updateChecked("userName", 1L, true);

        // Verify the results
    }

    @Test
    public void testReduceCartPreMoney() {
        // Setup
        // Run the test
        final double result = cartServiceImpl.reduceCartPreMoney("userName");

        // Verify the results
       System.out.println("result====="+result);
    }
    @Test
    public void testRedis(){
        Preferential preferential  =new Preferential();
            preferential.setBuyMoney(700.00);
            preferential.setPreMoney(100.00);
            preferential.setCategoryId(3L);
            preferential.setId(6L);
            preferential.setType("1");
            preferential.setStatus("0");
            preferential.setStartTime(DateUtil.parse("2023-01-11 00:00:00","yyyy-MM-dd HH:mm:ss"));
            preferential.setEndTime(DateUtil.parse("2023-01-22 23:59:59","yyyy-MM-dd HH:mm:ss"));
            redisTemplate.boundSetOps(CacheKey.PRE_LIST.toString()).add(preferential) ;
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme