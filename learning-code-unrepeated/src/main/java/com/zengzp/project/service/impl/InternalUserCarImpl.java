package com.zengzp.project.service.impl;

import com.zengzp.project.model.Car;
import com.zengzp.project.model.Item;
import com.zengzp.project.service.AbstractUserApi;
import com.zengzp.project.service.InternalUserCar;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 15:05
 * @description：内部用户逻辑实现
 * @modified By：
 * @version: 1$
 */
@Service(value = "internalUserService")
public class InternalUserCarImpl extends AbstractUserApi {
    @Override
    protected void reduceDeliveryPrice(long userId, Item item) {
        //内部用户免运费
        item.setDeliveryPrice(BigDecimal.ZERO);
    }

    @Override
    protected void reduceCouponPrice(long userId, Item item) {
        //无优惠
        item.setCouponPrice(BigDecimal.ZERO);
    }
}
