package com.wjc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.core.script.ScriptExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringJUnitConfig(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class luaTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void Test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);

        DefaultRedisScript script = new DefaultRedisScript("return redis.call('get', KEYS[1])", String.class);
        String res = (String) redisTemplate.execute(script, Collections.singletonList("name"));
        System.out.println("res = " + res);
    }

    @Test
    public void setTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
        System.out.println("redisTemplate = " + redisTemplate);
        ScriptExecutor executor = new DefaultScriptExecutor(redisTemplate);
        Object execute = executor.execute(new DefaultRedisScript("redis.call('set','name','nigger')"),null);

        redisTemplate.setScriptExecutor(executor);

        System.out.println("execute = " + execute);
    }
}
