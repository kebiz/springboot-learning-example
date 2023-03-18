package com.learning.code.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成订单失败消息体
 *
 */
@Data
public class OrderFailMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;


    public OrderFailMessage stockFail(Long orderId) {
        OrderFailMessage failMessage=new OrderFailMessage();
        failMessage.setOrderId(orderId);
        return failMessage;
    }
}
