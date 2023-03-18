package com.zengzp.product.config;

import cn.hutool.json.JSONUtil;
import com.learning.code.common.contant.OrderConstant;
import com.learning.code.common.contant.OrderQueueEnum;
import com.learning.code.common.contant.OrderQueueNameConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import javax.annotation.Resource;


@Slf4j
@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

   @Bean
    public AmqpTemplate amqpTemplate() {
        // 使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        // 消息发送失败返回到队列中，yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", JSONUtil.parse(message), replyCode, replyText,
                    exchange, routingKey);
        });
        // 消息确认，yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.info("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    /**
     * 订单消息实际消费队列所绑定的交换机
     */
    @Bean
    DirectExchange orderDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(OrderConstant.EXCHANGE_NAME)
                .durable(true)
                .build();
    }



    /**
     * 订单延迟队列队列所绑定的交换机
     */
   /* @Bean
    CustomExchange orderDelayDirect() {
        Map<String, Object> args = new HashMap<>(1);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(OrderConstant.DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }*/


    @Bean
    public Queue orderCreateFailQueue() {
        return new Queue(OrderQueueNameConstant.ORDER_CREATE_FAIL, true);
    }
    @Bean
    public Queue synStockDBQueue() {
        return new Queue(OrderQueueNameConstant.SYN_STOCK_DB, true);
    }


    /**
     * 将订单创建失败队列绑定到交换机
     */
    @Bean
    Binding orderCreateFailBinding(DirectExchange orderDirect, Queue orderCreateFailQueue) {
        return BindingBuilder
                .bind(orderCreateFailQueue)
                .to(orderDirect)
                .with(OrderQueueEnum.QUEUE_ORDER_CREATE_FAIL.getRouteKey());
    }
    /**
     * 将库存同步队列绑定到交换机
     */
    @Bean
    Binding synStockDBBinding(DirectExchange orderDirect, Queue synStockDBQueue) {
        return BindingBuilder
                .bind(synStockDBQueue)
                .to(orderDirect)
                .with(OrderQueueEnum.QUEUE_SYN_STOCK_DB.getRouteKey());
    }

}
