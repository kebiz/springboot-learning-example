package com.zengzp.product.mq;

import cn.hutool.core.date.DateUtil;
import com.learning.code.common.consumer.BaseConsumer;
import com.learning.code.common.contant.OrderQueueNameConstant;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.util.MessageHelper;
import com.learning.dubbo.MessageSendLogService;
import com.learning.dubbo.proxy.BaseConsumerProxy;
import com.rabbitmq.client.Channel;
import com.zengzp.product.mq.consumer.OrderConsumer;
import com.zengzp.product.mq.consumer.ReturnStockConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    @Reference(version = "1.2")
    private MessageSendLogService messageSendLogService;
    @Autowired
    private OrderConsumer orderConsumer;
    @Autowired
    private ReturnStockConsumer returnStockConsumer;
    @RabbitListener(queues = OrderQueueNameConstant.ORDER_CREATE)
    public void createOrder(CreateOrderMessage createOrderMessage ,Channel channel,Message message) throws IOException {
        log.info("==========生产================收到订单创建消息DeliveryTag:{},当前时间{},消息内容{}.", "",
                DateUtil.now(),MessageHelper.msgToObj(message,CreateOrderMessage.class));
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(orderConsumer, messageSendLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(createOrderMessage, channel,message);
        }
    }

    @RabbitListener(queues = OrderQueueNameConstant.ERROR_QUEUE)
    public void dealErrorMessage(CreateOrderMessage createOrderMessage ,Channel channel,Message message) throws IOException {
        log.info("=========生产==========收到回退库存消息:CorrelationId{},当前时间{},消息内容{}.", message.getMessageProperties().getCorrelationId(),
                DateUtil.now(),
                MessageHelper.msgToObj(message,CreateOrderMessage.class));
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(returnStockConsumer, messageSendLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(createOrderMessage, channel,message);
        }

    }
}
