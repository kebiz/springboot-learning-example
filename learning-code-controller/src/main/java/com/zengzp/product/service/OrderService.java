package com.zengzp.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learning.code.common.model.Order;
import com.learning.code.common.model.OrderDto;

public interface OrderService extends IService<Order> {
    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    Boolean createOrder(OrderDto orderDto);
    /**
     *失败消息重发
     */
    void doMessageSendLog();
    /**
     * 回退库存
     * @param orderId
     * @return
     */
     boolean returnStock(Long orderId);
}
