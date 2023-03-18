package com.zengzp.product.mq;

import cn.hutool.core.util.IdUtil;
import com.learning.code.common.contant.OrderQueueEnum;
import com.learning.code.common.model.BaseOrderMessage;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.model.OrderFailMessage;
import com.learning.code.common.model.SynStockDBMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/23 11:34
 * @description：订单消息发送类
 * @modified By：
 * @version: 1$
 */
@Slf4j
@Component
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 订单创建
     *
     * @param orderMessage
     * @return void
     */
    public void sendCreateOrderMessage(CreateOrderMessage orderMessage) {
        log.info("sendCreateOrderMessage:" + orderMessage.toString());
        convertAndSend(OrderQueueEnum.QUEUE_ORDER_CREATE, orderMessage);
    }
    /**
     *
     *订单创建失败
     * @param orderMessage
     * @return void
     */
    public void sendCreateOrderFailMessage(OrderFailMessage orderMessage) {
        log.info("sendCreateOrderFailMessage:" + orderMessage.toString());
        convertAndSend(OrderQueueEnum.QUEUE_ORDER_CREATE_FAIL, orderMessage);
    }
    /**
     *
     *同步库存数据
     * @param synStockDBMessage
     * @return void
     */
    public void sendSynStockDBMessage(SynStockDBMessage synStockDBMessage) {
        log.info("sendSynStockDBMessage:" + synStockDBMessage.toString());
        convertAndSend(OrderQueueEnum.QUEUE_SYN_STOCK_DB, synStockDBMessage);
    }
    private void convertAndSend(OrderQueueEnum queue, Object message) {
        CorrelationData correlationData = new CorrelationData(IdUtil.fastSimpleUUID());
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRouteKey(), message, correlationData);
    }


}
