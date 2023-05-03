package com.zengzp.product.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.learning.code.common.consumer.BaseConsumer;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.util.MessageHelper;
import com.rabbitmq.client.Channel;
import com.zengzp.product.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/4/20 16:21
 * @description：订单消费类
 * @modified By：
 * @version: 1$
 */
@Component
public class OrderConsumer implements BaseConsumer {
    @Autowired
    private OrderService orderService;
    @Override
    public void consume(Object object, Channel channel, Message message) throws IOException {
        //减库存 下订单 写入订单
        CreateOrderMessage createOrderMessage=(CreateOrderMessage)object;
        //CreateOrderMessage createOrderMessage = MessageHelper.msgToObj(message, CreateOrderMessage.class);
        Assert.notNull(createOrderMessage,"消息对象不能为空");
        boolean succ = orderService.createOrder(createOrderMessage.getOrderDto());
        if(!succ){
            throw new RuntimeException("订单创建失败");
        }
    }
}
