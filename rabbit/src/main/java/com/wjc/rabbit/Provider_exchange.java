package com.wjc.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
广播型
 */
public class Provider_exchange {
    public static void main(String[] args){
        //创建连接
        Connection connection = RabbitConnection.getRabbitConnection();
        try {
            //创建channel
            Channel channel = connection.createChannel();
            Map<String,Object> map=new HashMap<>();
            map.put("x-message-ttl",10000);
            map.put("x-expries",50000);
            //设置队列最大优先级，越大优先级越高，设置范围在0-255
            map.put("x-max-priority",255);
            AMQP.BasicProperties properties = new AMQP.BasicProperties();
            AMQP.BasicProperties.Builder builder = properties.builder();
            builder.priority(255);
            builder.expiration("5000");
            builder.deliveryMode(2);

            map.put("x-expries",50000);
            //如果队列消失，队列内的消息也消失

            //开启事务
            channel.txSelect();
            try{
                //发送消息
                channel.txCommit();
            } catch(IOException e){
                //如果有问题，事务回滚
                channel.txRollback();
                //消息重发或补偿机制
                //。。。。。
            }
            //开启publish comfirm模式
            channel.confirmSelect();
            //异步接收消息
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //正常 deliveryTag正常到达的消息编号，multiple是否批量回复
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    //不正常
                }
            });
            //发送消息
            //。。。。
            //同步等待消息的发送结果
            try {
                if (channel.waitForConfirms()){
                    //true为消息支持到达
                }else {
                    //false未正常到达
                }
            }catch (InterruptedException e){
                //报错也是未正常到达
            }

            //fanout扇形分叉交换机,direct路邮件，topic（我觉得是加了正则表达式的direct）
            channel.exchangeDeclare("exchange","direct");
            //2.路由件keyword
            channel.basicPublish("exchange","c",MessageProperties.PERSISTENT_TEXT_PLAIN,"asf".getBytes("utf-8"));

            //关闭连接
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
