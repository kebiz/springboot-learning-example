package com.zengzp.project.service;

import com.zengzp.project.model.Car;
import com.zengzp.project.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/7/12 16:10
 * @description：用户抽象类
 * @modified By：
 * @version: 1$
 */
public abstract class AbstractUserApi {
    /**
     * 计算运费
     *
     * @param item
     * @return
     */
    protected void reduceDeliveryPrice(long userId, Item item) {

    }

    /**
     * 计算商品优惠
     * @param userId
     * @param item
     */
    protected void reduceCouponPrice(long userId, Item item){

    }

    /**
     * 根据用户级别计算优惠价格、运费
     */
    public Car process(long userId, Map<Long, Integer> items){
        Car car=new Car();
        final List<Item> itemList=new ArrayList<Item>();
        items.entrySet().stream().forEach(entry -> {
            Item item=new Item();
            item.setId(entry.getKey());
            item.setQuantity(entry.getValue());
            item.setPrice(BigDecimal.valueOf(1000.0));
            itemList.add(item);
        });
        car.setItems(itemList);

        itemList.stream().forEach(item -> {
            //运费是商品总价的10%
            reduceDeliveryPrice(userId,item);
            //优惠价是0
            reduceCouponPrice(userId,item);
        });

        //计算运费总价
        car.setTotalDeliveryPrice(itemList.stream().map(Item::getDeliveryPrice).reduce(BigDecimal.ZERO,BigDecimal::add));
        //计算总优惠
        car.setTotalDiscount(itemList.stream().map(Item::getCouponPrice).reduce(BigDecimal.ZERO,BigDecimal::add));
        //计算商品总价
        car.setTotalItemPrice(itemList.stream().map(item -> BigDecimal.valueOf(item.getQuantity()).multiply(item.getPrice())).reduce(BigDecimal.ZERO,BigDecimal::add));
        //计算实际支付价格
        car.setPayPrice(car.getTotalItemPrice().add(car.getTotalDeliveryPrice()).subtract(car.getTotalDiscount()));
        return car;
    }
}
