package com.learning.dubbo.proxy;

import com.learning.code.common.model.MessageSendLog;
import com.learning.dubbo.MessageSendLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2023/4/20 15:28
 * @description：代理基类
 * @modified By：
 * @version: 1$
 */
@Slf4j
public class BaseConsumerProxy {
    private Object target;
    private MessageSendLogService messageSendLogService;

    public BaseConsumerProxy(Object target, MessageSendLogService messageSendLogService){
        this.target=target;
        this.messageSendLogService=messageSendLogService;
    }

    public Object getProxy(){
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        Object proxy1=Proxy.newProxyInstance(classLoader,interfaces,((proxy, method, args) -> {
            Object object  =(Object)args[0];
            Channel channel  =(Channel)args[1];
            Message message  =(Message)args[2];
            String correlationId = getCorrelationId(message);
            // 消费幂等性, 防止消息被重复消费
            if (isConsumed(correlationId)) {
                log.info("重复消费, correlationId: {}", correlationId);
                return null;
            }
            MessageProperties properties = message.getMessageProperties();
            long tag = properties.getDeliveryTag();
            try {
                // 真正消费的业务逻辑
                Object result = method.invoke(target, args);
                messageSendLogService.updateMsgStatus(correlationId, "3");
                // 消费确认
                channel.basicAck(tag, false);
                return result;
            } catch (Exception e) {
                log.error("getProxy error", e);
                channel.basicNack(tag, false, false);
                return null;
            }
        }));
        return proxy1;
    }
    /**
     * 获取CorrelationId
     *
     * @param message
     * @return
     */
    private String getCorrelationId(Message message) {
        String correlationId = null;
        MessageProperties properties = message.getMessageProperties();
        Map<String, Object> headers = properties.getHeaders();
        for (Map.Entry entry : headers.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("spring_returned_message_correlation")) {
                correlationId = value;
            }
        }

        return correlationId;
    }

    /**
     * 消息是否已被消费
     *
     * @param correlationId
     * @return
     */
    private boolean isConsumed(String correlationId) {
        MessageSendLog msgSendLog = messageSendLogService.getMsgSendLogByMsgId(correlationId);
        if (null == msgSendLog || msgSendLog.getSendStatus().equals("3")) {
            return true;
        }

        return false;
    }
}
