package com.wjc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringJUnitConfig(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class redis {

    @Test
    public void redisTest(){
        //通过配置文件创建redis对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        RedisTemplate bean = context.getBean(RedisTemplate.class);

        //存取值
        bean.opsForValue().set("pussy","123.111");
        Object pussy = bean.opsForValue().get("pussy");
        System.out.println("pussy = " + pussy.toString());


    }

}
