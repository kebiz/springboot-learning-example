package com.learning.code.common.contant;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/23 11:05
 * @description：order常量类
 * @modified By：
 * @version: 1$
 */
public class OrderConstant {
    /**
     * 订单模块默认交换机
     */
    public  static final String EXCHANGE_NAME = "learning.order.direct";
    /**
     * 订单延迟队列交换机
     */
    public  static final String DELAY_EXCHANGE_NAME = "learning.order.delay.direct";
    /**
     * 异常默认交换机
     */
    public  static final String ERROR_EXCHANGE_NAME = "learning.order.error.direct";
}
