package com.zengzp.product.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.learning.code.common.consumer.BaseConsumer;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.util.MessageHelper;
import com.rabbitmq.client.Channel;
import com.zengzp.product.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/4/21 10:08
 * @description：回退库存
 * @modified By：
 * @version: 1$
 */
@Slf4j
@Component
public class ReturnStockConsumer implements BaseConsumer {
    @Autowired
    private OrderService orderService;
    @Override
    public void consume(Message message, Channel channel) throws IOException {
        //减库存 下订单 写入订单
        CreateOrderMessage createOrderMessage = MessageHelper.msgToObj(message, CreateOrderMessage.class);
        Assert.notNull(createOrderMessage,"消息对象不能为空");
        boolean succ = orderService.returnStock(createOrderMessage.getOrderId());
        if(!succ){
            throw new RuntimeException("回退库存失败");
        }
    }
}
