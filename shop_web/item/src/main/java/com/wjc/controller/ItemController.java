package com.wjc.controller;

import com.wjc.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/createHtml")
    @ResponseBody
    public boolean createHtml(@RequestBody Goods goods){
        try {
            //获取模板页面
            Template template = configuration.getTemplate("goods.ftl");

            Map<String,Object> map=new HashMap<>();
            map.put("goods",goods);

            String path = ItemController.class.getResource("/static/html").getPath();
            System.out.println("path = " + path);

            FileWriter writer = new FileWriter(path+"/"+goods.getId()+".html");

            template.process(map,writer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return true;
    }
}
