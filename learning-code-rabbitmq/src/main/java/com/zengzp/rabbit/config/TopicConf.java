package com.zengzp.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/12/8 17:13
 * @description：topic类型
 * @modified By：
 * @version: 1$
 */
@Configuration
public class TopicConf {
    @Bean
    public Queue addElasticSearchDataQueue(){
        return new Queue("elasticsearch.add.queue");
    }
    @Bean
    public Queue delElasticSearchDataQueue(){
        return new Queue("elasticsearch.del.queue");
    }
    @Bean
    public Queue addGoodsDetailQueue(){
        return new Queue("goodsDetail.add.queue");
    }
    @Bean
    public Queue delGoodsDetailQueue(){
        return new Queue("goodsDetail.del.queue");
    }
    @Bean
    public TopicExchange elasticsearchExchange(){
        return new TopicExchange("elasticsearchExchange");
    }
    @Bean
    public TopicExchange goodsDetailExchange(){
        return new TopicExchange("goodsDetailExchange");
    }
    @Bean
    public Binding bindAddElasticsearchExchange(Queue addElasticSearchDataQueue,TopicExchange elasticsearchExchange){
        return BindingBuilder.bind(addElasticSearchDataQueue).to(elasticsearchExchange).with("elasticsearch.add.*");
    }
    @Bean
    public Binding bindDelElasticsearchExchange(Queue delElasticSearchDataQueue,TopicExchange elasticsearchExchange){
        return BindingBuilder.bind(delElasticSearchDataQueue).to(elasticsearchExchange).with("elasticsearch.del.*");
    }
    @Bean
    public Binding bindAddGoodsDetailExchange(Queue addGoodsDetailQueue,TopicExchange goodsDetailExchange){
        return BindingBuilder.bind(addGoodsDetailQueue).to(goodsDetailExchange).with("goodsDetail.add.*");
    }
    @Bean
    public Binding bindDelGoodsDetailExchange(Queue delGoodsDetailQueue,TopicExchange goodsDetailExchange){
        return BindingBuilder.bind(delGoodsDetailQueue).to(goodsDetailExchange).with("goodsDetail.del.*");
    }
}
