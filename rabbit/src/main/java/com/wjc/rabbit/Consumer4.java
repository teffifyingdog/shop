package com.wjc.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer4 {
    public static void main(String[] args) throws IOException {
        //创建连接
        Connection connection = RabbitConnection.getRabbitConnection();

        //创建channel
        Channel channel = connection.createChannel();

        //创建队列
        channel.queueDeclare("c_queue",false,false,false,null);

        //将队列绑定交换机
        channel.queueBind("c_queue","exchange","b");
//        channel.exchangeBind();交换机绑定交换机

        channel.basicConsume("c_queue",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body4 = " + new String(body,"utf-8"));
            }
        });


    }
}
