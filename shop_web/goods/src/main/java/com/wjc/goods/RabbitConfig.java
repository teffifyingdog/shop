package com.wjc.goods;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //bean的名称默认方法名
    //创建solr查找队列
//    @Bean
//    public Queue getSearchQueue(){
//        return new Queue("search_queue");
//    }
//
//    //创建生成html的队列
//    @Bean
//    public Queue getItemQueue(){
//        return new Queue("item_queue");
//    }

    //创建交换机
    @Bean
    public DirectExchange getExchange(){
        return new DirectExchange("exchange");
    }

    //绑定search队列和交换机(normal)
//    @Bean
//    public Binding bing_search_exchange(Queue getSearchQueue, DirectExchange getExchange){
//        return BindingBuilder.bind(getSearchQueue).to(getExchange).with("normal");
//    }
//
//    //绑定search队列和交换机(seckill)
//    @Bean
//    public Binding bing_search_exchange2(Queue getSearchQueue, DirectExchange getExchange){
//        return BindingBuilder.bind(getSearchQueue).to(getExchange).with("seckill");
//    }
//
//    //绑定item队列和交换机(normal)
//    @Bean
//    public Binding bing_item_exchange(Queue getItemQueue, DirectExchange getExchange){
//        return BindingBuilder.bind(getItemQueue).to(getExchange).with("normal");
//    }



}
