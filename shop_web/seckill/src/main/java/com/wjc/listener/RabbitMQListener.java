package com.wjc.listener;

import com.wjc.controller.SeckillController;
import com.wjc.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitMQListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(name = "getExchange",type = "direct",durable = "true"),
                    value = @Queue(name = "seckill_queue",durable = "true"),
                    key = "seckill"
            )
    )
    public void msgHandler(Goods goods) {
        try {
            //获取模板页面
            Template template = configuration.getTemplate("seckill.ftl");

            Map<String,Object> map=new HashMap<>();
            map.put("goods",goods);

            //获得targer路径
            String path = SeckillController.class.getResource("/static/html").getPath();
            System.out.println("path = " + path);

            //将结果输出到target
            FileWriter writer = new FileWriter(path+"/"+goods.getId()+".html");

            template.process(map,writer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
