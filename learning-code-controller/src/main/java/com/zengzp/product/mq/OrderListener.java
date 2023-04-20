package com.zengzp.product.mq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.learning.code.common.consumer.BaseConsumer;
import com.learning.code.common.contant.OrderQueueNameConstant;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.model.MessageSendLog;
import com.learning.code.common.model.OrderFailMessage;
import com.learning.code.common.proxy.BaseConsumerProxy;
import com.learning.code.common.util.MessageHelper;
import com.learning.dubbo.MessageSendLogService;
import com.rabbitmq.client.Channel;
import com.zengzp.product.mq.consumer.OrderConsumer;
import com.zengzp.product.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
    @Reference(version = "1.2")
    private MessageSendLogService messageSendLogService;
    @Autowired
    private OrderConsumer orderConsumer;
    @RabbitListener(queues = OrderQueueNameConstant.ORDER_CREATE)
    public void createOrder(Channel channel, Message message) throws IOException {
        log.info("==========生产================收到订单创建消息DeliveryTag:{},当前时间{},消息内容{}.", message.getMessageProperties().getDeliveryTag(),
                DateUtil.now(),
                MessageHelper.msgToObj(message,CreateOrderMessage.class));
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(orderConsumer, messageSendLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }

    @RabbitListener(queues = OrderQueueNameConstant.ERROR_QUEUE)
    public void dealErrorMessage(CreateOrderMessage createOrderMessage, Channel channel, Message message) throws IOException {
        log.info("=========生产==========收到订单创建失败消息:CorrelationId{},当前时间{},消息内容{}.", message.getMessageProperties().getCorrelationId(),
                DateUtil.now(),
                createOrderMessage.toString());
        try {

           // messageSendLogService.saveMsgSendLog(keyUpper, JSONUtil.toJsonPrettyStr(createOrderMessage),"-2");
            /* if(orderService.returnStock(createOrderMessage.getOrderId())){
               log.info("订单回退执行成功: deliveryTag{}", message.getMessageProperties().getDeliveryTag());
               channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
           }else {
               throw new RuntimeException("订单回退报错");
           }*/
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            log.info("处理异常队列消息失败: deliveryTag{},发送邮件到管理员", message.getMessageProperties().getDeliveryTag());

            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }

    }
}
