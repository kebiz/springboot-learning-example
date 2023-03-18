package com.learning.code.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成订单消息体
 *
 */
@Data
public class CreateOrderMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private OrderDto orderDto;
    private Long orderId;
    private String  userId;

}
