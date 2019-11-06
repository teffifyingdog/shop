package com.wjc.listener;

import com.wjc.controller.SearchController;
import com.wjc.entity.Goods;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class RabbitMQListener {
    @Autowired
    private SearchController searchController;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search_queue",durable = "true"),
            exchange = @Exchange(name = "getExchange"),
            key = "normal"
    ))
    public void goodsMessageHandler(Goods goods){
        System.out.println("接收到"+goods);
        searchController.insert(goods);
    }
}
