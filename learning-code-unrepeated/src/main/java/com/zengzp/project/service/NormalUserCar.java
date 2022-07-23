package com.zengzp.project.service;

import com.zengzp.project.model.Car;

import java.util.Map;

/**
 * 普通用户购物车逻辑
 */
public interface NormalUserCar {
    public Car process(long userId, Map<Long,Integer> items);

}
