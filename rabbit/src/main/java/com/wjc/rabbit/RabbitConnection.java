package com.wjc.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConnection {
    private static ConnectionFactory factory;

    static {
        //创建连接工厂
        factory = new ConnectionFactory();
        factory.setHost("192.168.252.222");//rabbitmq所在服务器的ip
        factory.setPort(5672);//rabbitmq的端口
        factory.setUsername("wjc");//用户名
        factory.setPassword("369");//密码
        factory.setVirtualHost("/wjc");//虚拟请求
    }

    //返回rabbit连接对象
    public static Connection getRabbitConnection(){
        Connection connection=null;
        try {
            //通过静态的工厂生成连接对象
            connection=factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
