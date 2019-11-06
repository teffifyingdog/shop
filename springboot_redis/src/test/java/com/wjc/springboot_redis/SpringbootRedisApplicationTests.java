package com.wjc.springboot_redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpringbootRedisApplicationTests {

    @Autowired//注意这边不能叫redis，好像aop里面已经有一个redis对象了
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        //可以数字，字符串
        redisTemplate.opsForValue().set("dog","cat");
        String dog = (String) redisTemplate.opsForValue().get("dog");
        System.out.println("dog = " + dog);

        //只能字符串
        stringRedisTemplate.opsForValue().set("cat","dog");
        String cat = stringRedisTemplate.opsForValue().get("cat");
        System.out.println("cat = " + cat);

    }

}
