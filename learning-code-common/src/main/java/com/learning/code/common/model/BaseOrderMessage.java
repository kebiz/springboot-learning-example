package com.learning.code.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 基础订单消息体
 *
 */
@Data
@Accessors(chain = true)
public class BaseOrderMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
}
