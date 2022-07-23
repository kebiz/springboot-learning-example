package com.zengzp.project.service.impl;

import com.zengzp.project.model.Car;
import com.zengzp.project.model.Item;
import com.zengzp.project.service.AbstractUserApi;
import com.zengzp.project.service.VipUserCar;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 14:29
 * @description：vip用户逻辑实现类
 * @modified By：
 * @version: 1$
 */
@Service(value = "vipUserService")
public class VipUserCarImpl extends AbstractUserApi {
    @Override
    protected void reduceDeliveryPrice(long userId, Item item) {
        //运费是商品总价的10%
        item.setDeliveryPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(0.1))));
    }

    @Override
    protected void reduceCouponPrice(long userId, Item item) {
        //购买两件以上的物品，第三件开始打折
        if(item.getQuantity()>2){
            item.setCouponPrice(item.getPrice().multiply(new BigDecimal("0.8")).multiply(BigDecimal.valueOf(item.getQuantity()-2)));
        }else {
            item.setCouponPrice(BigDecimal.ZERO);
        }
    }

}
