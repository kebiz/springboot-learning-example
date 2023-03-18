package com.zengzp.product.mq;

import cn.hutool.core.date.DateUtil;
import com.learning.code.common.contant.OrderQueueNameConstant;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.model.OrderFailMessage;
import com.rabbitmq.client.Channel;
import com.zengzp.product.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/28 10:28
 * @description：订单消费者监听类
 * @modified By：
 * @version: 1$
 */
@Slf4j
@Component
public class OrderListener {
    @Autowired
    private OrderService orderService;
    @RabbitListener(queues = OrderQueueNameConstant.ORDER_CREATE)
    public void createOrder(CreateOrderMessage orderMessage, Channel channel, Message message) throws IOException {
        log.info("==========生产================收到订单创建消息:deliveryTag{},当前时间{},消息内容{}.", message.getMessageProperties().getDeliveryTag(),
                DateUtil.now(),
                orderMessage.toString());
        try {
            //减库存 下订单 写入订单

            Boolean succ = orderService.createOrder(orderMessage.getOrderDto());
            if (succ) {
                log.info("订单创建执行成功: deliveryTag{}", message.getMessageProperties().getDeliveryTag());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            } else {
                throw new RuntimeException("订单创建失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info("订单创建执行失败: deliveryTag{}", message.getMessageProperties().getDeliveryTag());

            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = OrderQueueNameConstant.ORDER_CREATE_FAIL)
    public void createFailOrder(OrderFailMessage orderFailMessage, Channel channel, Message message) throws IOException {
        log.info("=========生产==========收到订单创建失败消息:deliveryTag{},当前时间{},消息内容{}.", message.getMessageProperties().getDeliveryTag(),
                DateUtil.now(),
                orderFailMessage.toString());
        try {
           if(orderService.returnStock(orderFailMessage.getOrderId())){
               log.info("订单回退执行成功: deliveryTag{}", message.getMessageProperties().getDeliveryTag());
               channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
           }else {
               throw new RuntimeException("订单回退报错");
           }
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            log.info("订单回退执行失败: deliveryTag{}", message.getMessageProperties().getDeliveryTag());

            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }

    }
}
