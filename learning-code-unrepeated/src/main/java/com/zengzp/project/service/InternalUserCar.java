package com.zengzp.project.service;

import com.zengzp.project.model.Car;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 内部用户购物车
 */
public interface InternalUserCar {
    public Car process(long userId, Map<Long,Integer> items);
}
