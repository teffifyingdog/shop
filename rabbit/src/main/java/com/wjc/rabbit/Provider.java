package com.wjc.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.252.222");//rabbitmq所在服务器的ip
        factory.setPort(5672);//rabbitmq的端口
        factory.setUsername("wjc");//用户名
        factory.setPassword("369");//密码
        factory.setVirtualHost("/wjc");//虚拟请求

        //通过工厂连接对象
        Connection connection = factory.newConnection();

        //通过连接对象生成channel
        Channel channel = connection.createChannel();

        //通过channel生成队列
        //1.queue:队列名称，2.durable：是否持久化，3.exclusive:是否排外
        //4.autoDelete：是否自动删除，5.argument：什么时候自动删除
        channel.queueDeclare("w_queue",false,false,false,null);

        //发送消息到队列
        for (int i = 0; i < 10; i++) {
            String info=i+"个sb";
            //1.交换机，因为这个提供者直接传到队列，所有为空
            //2.队列名称
            //3.跟持久化有关的配置
            //4.内容
            channel.basicPublish("","w_queue",null,info.getBytes("utf-8"));
        }

        //关闭连接
        connection.close();
    }
}
