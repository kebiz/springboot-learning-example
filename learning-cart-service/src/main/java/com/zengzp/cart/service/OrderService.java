package com.zengzp.cart.service;

import com.zengzp.cart.entity.Order;
import com.zengzp.cart.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     * 新增订单
     * @param order
     * @return
     */
    Map<String,Object> add(Order order);
}
