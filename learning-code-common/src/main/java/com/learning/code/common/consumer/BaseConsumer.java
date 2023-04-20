package com.learning.code.common.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/4/20 16:15
 * @description：消费者基类
 * @modified By：
 * @version: 1$
 */
public interface BaseConsumer {
    void consume(Message message, Channel channel) throws IOException;
}
