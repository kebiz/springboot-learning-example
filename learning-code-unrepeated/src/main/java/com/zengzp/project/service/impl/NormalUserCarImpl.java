package com.zengzp.project.service.impl;

import com.zengzp.project.model.Car;
import com.zengzp.project.model.Item;
import com.zengzp.project.service.AbstractUserApi;
import com.zengzp.project.service.NormalUserCar;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 11:18
 * @description：普通用户实现类
 * @modified By：
 * @version: 1$
 */
@Service(value = "normalUserService")
public class NormalUserCarImpl extends AbstractUserApi {
    @Override
    protected void reduceDeliveryPrice(long userId, Item item) {
        //运费是商品总价的10%
        item.setDeliveryPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(0.1))));
    }

    @Override
    protected void reduceCouponPrice(long userId, Item item) {
        //优惠价是0
        item.setCouponPrice(BigDecimal.ZERO);
    }
}
