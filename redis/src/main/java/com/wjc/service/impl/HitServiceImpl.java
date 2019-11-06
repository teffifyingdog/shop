package com.wjc.service.impl;

import com.wjc.dao.HitMapper;
import com.wjc.entity.Hit;
import com.wjc.service.IHitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@CacheConfig(cacheNames = "hit")
public class HitServiceImpl implements IHitService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private HitMapper hitDao;

    // TODO 添加分布式锁的lua脚本
    private String locklua="--使用lua脚本添加分布式锁\n" +
            "--需要的变量\n" +
            "local lockName = KEYS[1]\n" +
            "local lockValue = ARGV[1]\n" +
            "local lockTimeOut = ARGV[2]\n" +
            "\n" +
            "\n" +
            "--设置分布式锁\n" +
            "local result = redis.call('setnx', lockName, lockValue)\n" +
            "\n" +
            "\n" +
            "--判断是否获得分布式锁\n" +
            "if result == 1 then\n" +
            "     --获得分布式锁，添加超时时间\n" +
            "    redis.call('expire', lockName, lockTimeOut)\n" +
            "    --返回1表示获得分布式锁    \n" +
            "    return \"1\"\n" +
            "else\n" +
            "    --没有获得分布式锁\n" +
            "    return \"0\"        \n" +
            "end";

    // TODO 删除锁的lua脚本
    private String lockDelLua="--删除分布式锁的lua脚本\n" +
            "--获得变量\n" +
            "local lockName = KEYS[1]\n" +
            "local uuid = ARGV[1]\n" +
            "\n" +
            "\n" +
            "--获得锁的value\n" +
            "local lockValue = redis.call('get', lockName)\n" +
            "\n" +
            "\n" +
            "--判断锁的value和添加锁时的uuid是否一致\n" +
            "if lockValue == uuid then\n" +
            "    --说明锁时当前线程添加\n" +
            "    redis.call('del', lockName);\n" +
            "    --返回成功\n" +
            "    return '1'\n" +
            "end";

    @Override
    public int hitTest() {

        //分布式锁的value值
        String uuid = UUID.randomUUID().toString();

        //判断是否有锁,如果能够添加，说明无锁
        String flag = (String) redisTemplate.execute(new DefaultRedisScript(locklua,String.class), Collections.singletonList("hit_lock"),uuid,"60");
        if (Integer.parseInt(flag)==1) {

            //设置超时时间，这个可以在上个方法创建一起设置，但为了体现lua脚本，在这设置，原理和lua脚本差不多
            redisTemplate.expire("hit_lock",5, TimeUnit.SECONDS);

            //访问+1
            Hit hit = hitDao.selectById(1);
            hit.setHit(hit.getHit()+1);
            int res = hitDao.updateById(hit);

            //lua脚本删除锁
            String result= (String) redisTemplate.execute(new DefaultRedisScript(lockDelLua,String.class),Collections.singletonList("hit_lock"),uuid);
            System.out.println("result = " + result);
            return res;
        }else {
            try {
                //如果有锁则等待0.05s，再调用这个方法
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.hitTest();
        }
    }

    @Override
    @Cacheable(key = "'list'",unless = "5>1",condition = "1>2")
    //unless:如果redis里语句有缓存了，还是直接从缓存中拿
    //condition：如果unless为true，condition怎么都缓存不到redis
    public List<Hit> annotationTest() {
        System.out.println("查表");
        return hitDao.selectList(null);
    }
}
