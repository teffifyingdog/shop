package com.wjc.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer2 {
    //创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。如果没有可用的线程，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
    public static ExecutorService executorService= Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        //获得连接对象
        Connection connection = RabbitConnection.getRabbitConnection();
        //创建channel
        Channel channel = connection.createChannel();
        //通过channel生成队列，防止消费者在提供者创建队列之前运行，而报错，无队列
        channel.queueDeclare("w_queue",false,false,false,null);
        //监听队列，接收输出结果，body是个byte【】
        channel.basicConsume("w_queue",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //通过线程池运行多个接收
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("body2 = " + new String(body,"utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        try {
                            //证明接收数据是单线程的
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }
}
