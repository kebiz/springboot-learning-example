package com.zengzp.rabbit.service;

import lombok.extern.slf4j.Slf4j;
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
public class GoodsDetailService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendGoodsDetailAddMessage(){
        log.info("=========goodsDetailExchange send to add queue==============");
        amqpTemplate.convertAndSend("goodsDetailExchange","goodsDetail.add.queue","~~goodsDetailExchange send to add queue~~");
    }

    public void sendGoodsDetailDelMessage(){
        log.info("==========goodsDetailExchange send to del queue============");
        amqpTemplate.convertAndSend("goodsDetailExchange","goodsDetail.del.queue","~~goodsDetailExchange send to del queue~~");
    }
}
