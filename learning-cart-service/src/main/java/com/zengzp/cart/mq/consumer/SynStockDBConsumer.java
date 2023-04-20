package com.zengzp.cart.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.learning.code.common.consumer.BaseConsumer;
import com.learning.code.common.model.SynStockDBMessage;
import com.learning.code.common.util.MessageHelper;
import com.rabbitmq.client.Channel;
import com.zengzp.cart.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/4/20 17:31
 * @description：数据同步
 * @modified By：
 * @version: 1$
 */
@Component
@Slf4j
public class SynStockDBConsumer implements BaseConsumer {
    @Autowired
    private SkuService skuService;
    @Override
    public void consume(Message message, Channel channel) throws IOException {
        try {
            SynStockDBMessage synStockDBMessage = MessageHelper.msgToObj(message, SynStockDBMessage.class);
            Assert.notNull(synStockDBMessage,"消息对象不能为空");
            Assert.notNull(synStockDBMessage.getOrderId(), "订单ID不能为空");
            Assert.notEmpty(synStockDBMessage.getStockDtoList(), "扣减库存数据不能为空");
            skuService.synStockDB(synStockDBMessage.getStockDtoList());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
           throw new RuntimeException("消息同步失败");
        }
    }
}
