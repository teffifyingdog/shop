package com.wjc.email;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabiitMQ {

    @Bean
    public Queue getEmailQueue(){
        return new Queue("queue_email");
    }

    @Bean
    public FanoutExchange getEmailExchange(){
        return new FanoutExchange("exchange_email");
    }

    @Bean
    public Binding bing_email_exchange(Queue getEmailQueue, FanoutExchange getEmailExchange){
        return BindingBuilder.bind(getEmailQueue).to(getEmailExchange);
    }
}
