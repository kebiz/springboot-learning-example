package com.zengzp.cart.mq;

import cn.hutool.core.util.IdUtil;
import com.learning.code.common.contant.OrderQueueEnum;
import com.learning.code.common.model.BaseOrderMessage;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.model.OrderFailMessage;
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
     * 订单超时未支付自动取消
     *
     * @param message
     * @param expiration
     * @return void
     */
    public void sendAutoCancelOrderMessage(BaseOrderMessage message, long expiration) {
        log.info("sendAutoCancelOrderMessage:" + message);
        log.info("send time:" + LocalDateTime.now());
        convertAndSend(OrderQueueEnum.QUEUE_ORDER_AUTO_CANCEL, message, new ExpirationMessagePostProcessor(expiration));
    }
    private void convertAndSend(OrderQueueEnum queue, Object message) {
        CorrelationData correlationData = new CorrelationData(IdUtil.fastSimpleUUID());
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRouteKey(), message, correlationData);
    }

    private void convertAndSend(OrderQueueEnum queue, Object message, MessagePostProcessor messagePostProcessor) {
        CorrelationData correlationData = new CorrelationData(IdUtil.fastSimpleUUID());
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRouteKey(), message, messagePostProcessor,
                correlationData);
    }
}
