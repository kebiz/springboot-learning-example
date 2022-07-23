package com.zengzp.project.service;

import com.zengzp.project.model.Car;

import java.util.Map;

/**
 * Vip用户购物车逻辑
 */
public interface VipUserCar {
    public Car process(long userId, Map<Long,Integer> items);
}
