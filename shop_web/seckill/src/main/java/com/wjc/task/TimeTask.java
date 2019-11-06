package com.wjc.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //cron表达式(秒 分 时 天 月 【星期】 年)
    @Scheduled(cron = "0 0 0/1 * * *")
    public void seckillStart() {
        System.out.println("what fuck is that?");
        redisTemplate.executePipelined(new SessionCallback() {

            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                return null;
            }
        });
    }
}
