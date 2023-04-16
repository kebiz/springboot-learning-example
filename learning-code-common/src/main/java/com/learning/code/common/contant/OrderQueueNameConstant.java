package com.learning.code.common.contant;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/23 11:13
 * @description：订单队列名称常量类
 * @modified By：
 * @version: 2$
 */
public class OrderQueueNameConstant {
    /**
     * 生成订单
     */
    public static final String ORDER_CREATE = "learning.order.create";
    /**
     * 生成订单失败
     */
    public static final String ORDER_CREATE_FAIL = "learning.order.create.fail";
    /**
     * 取消订单
     */
    public static final String ORDER_AUTO_CANCEL = "learning.order.auto.cancel";

    /**
     * 同步库存
     */
    public static final String SYN_STOCK_DB = "learning.syn.stock.db";
    /**
     * 异常消息队列
     */
    public static final String ERROR_QUEUE = "error_queue";

}
