package com.wjc.item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    /**
     * 注入freemarker的配置对象
     */
    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() {
        try {
            //获取静态模板
            Template test = configuration.getTemplate("test.ftl");
            //key-value数据集合
            Map map = new HashMap<>();
            map.put("key","value");
            //输出结果,到某个文件夹
            test.process(map,new FileWriter("C:\\Users\\Administrator\\Desktop\\study_note\\test.html"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
