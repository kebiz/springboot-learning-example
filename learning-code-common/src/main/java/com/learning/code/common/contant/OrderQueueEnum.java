package com.learning.code.common.contant;


import lombok.Getter;

@Getter
public enum OrderQueueEnum {
    /**
     * 创建订单
     */
    QUEUE_ORDER_CREATE(OrderConstant.EXCHANGE_NAME, OrderQueueNameConstant.ORDER_CREATE, "learning.order.create"),
    /**
     * 创建订单失败
     */
    QUEUE_ORDER_CREATE_FAIL(OrderConstant.EXCHANGE_NAME, OrderQueueNameConstant.ORDER_CREATE_FAIL,
            "learning.order.create.fail"),
    /**
     * 同步库存
     */
    QUEUE_SYN_STOCK_DB(OrderConstant.EXCHANGE_NAME, OrderQueueNameConstant.SYN_STOCK_DB,
            "learning.syn.stock.db"),
    /**
     * 取消订单
     */
    QUEUE_ORDER_AUTO_CANCEL(OrderConstant.DELAY_EXCHANGE_NAME, OrderQueueNameConstant.ORDER_AUTO_CANCEL,
            "gruul.order.auto.cancel"),
    /**
     * 异常队列
     */
    QUEUE_ERROR(OrderConstant.ERROR_EXCHANGE_NAME, OrderQueueNameConstant.ERROR_QUEUE,
            "error"),
    ;

    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    OrderQueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
