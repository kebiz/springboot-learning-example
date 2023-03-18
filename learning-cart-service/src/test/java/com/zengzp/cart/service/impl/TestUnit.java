package com.zengzp.cart.service.impl;
import cn.hutool.json.JSONUtil;
import com.learning.code.common.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/23 16:34
 * @description：测试类
 * @modified By：
 * @version: 1$
 */
@RunWith(SpringRunner.class)
public class TestUnit {
    @Test
    public void testGetJSON(){
        Order order=new Order();
        order.setUserName("userName");
        order.setTotalNum(23);
        order.setPayStatus("1");
        order.setCreateTime(new Date());
        System.out.println("JSON===="+JSONUtil.toJsonStr(order));
    }
}
