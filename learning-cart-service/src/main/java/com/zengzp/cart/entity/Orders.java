package com.zengzp.cart.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/3 15:43
 * @description：订单业务表
 * @modified By：
 * @version: 1$
 */
@Data
public class Orders implements Serializable {
    private Order order;
    private List<OrderItem> orderItems;
}
