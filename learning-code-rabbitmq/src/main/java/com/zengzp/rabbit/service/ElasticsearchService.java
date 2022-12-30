package com.zengzp.rabbit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/9 10:03
 * @description：更新elasticsearch发送端
 * @modified By：
 * @version: 1$
 */
@Service
@Slf4j
public class ElasticsearchService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendElasticsearchAddMessage(){
        log.info("=========elasticsearchExchange send to add queue==============");
        amqpTemplate.convertAndSend("elasticsearchExchange","elasticsearch.add.queue","~~elasticsearchExchange send to add queue~~");
    }

    public void sendElasticsearchDelMessage(){
        log.info("==========elasticsearchExchange send to del queue============");
        amqpTemplate.convertAndSend("elasticsearchExchange","elasticsearch.del.queue","~~elasticsearchExchange send to del queue~~");
    }
}
