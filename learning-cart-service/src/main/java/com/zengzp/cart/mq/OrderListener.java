package com.zengzp.cart.mq;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.learning.code.common.contant.OrderQueueNameConstant;
import com.learning.code.common.model.CreateOrderMessage;
import com.learning.code.common.model.MessageSendLog;
import com.learning.code.common.model.OrderFailMessage;
import com.learning.code.common.model.SynStockDBMessage;
import com.learning.dubbo.MessageSendLogService;
import com.rabbitmq.client.Channel;
import com.zengzp.cart.service.OrderTestService;
import com.zengzp.cart.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.houtu.util.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/2/23 15:14
 * @description：rabbit客户款消费类
 * @modified By：
 * @version: 1$
 */
@Slf4j
@Component
public class OrderListener {
    @Autowired
    private SkuService skuService;
    @Resource
    private MessageSendLogService messageSendLogService;
    @RabbitListener(queues = OrderQueueNameConstant.SYN_STOCK_DB)
    public void createOrder(SynStockDBMessage synStockDBMessage, CorrelationData correlationData, Channel channel, Message message) throws IOException {
        log.info("=========生产==========收到同步库存数据消息:deliveryTag{},当前时间{},消息内容{}.", message.getMessageProperties().getDeliveryTag(),
                DateUtil.now(),
                synStockDBMessage.toString());
        try {
            Assert.notNull(synStockDBMessage.getOrderId(),"订单ID不能为空");
            Assert.notEmpty(synStockDBMessage.getStockDtoList(),"扣减库存数据不能为空");
            //判断幂等性
            MessageSendLog msgSendLog = messageSendLogService.getMsgSendLogByMsgId(correlationData.getId());
            if(msgSendLog==null || "3".equals(msgSendLog.getSendStatus())){
                log.info("重复消费-msgId：{}",correlationData.getId());
                return;
            }
            skuService.synStockDB(synStockDBMessage.getStockDtoList());
            log.info("同步库存执行成功: deliveryTag{}", message.getMessageProperties().getDeliveryTag());
            messageSendLogService.updateMsgStatus(correlationData.getId(),"3");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            log.info("同步库存执行失败: deliveryTag{}", message.getMessageProperties().getDeliveryTag());

            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
