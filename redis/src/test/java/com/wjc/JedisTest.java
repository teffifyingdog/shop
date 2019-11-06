package com.wjc;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
    public static void main(String[] args){
        //jedis连接池配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);   //最大连接数
        jedisPoolConfig.setMinIdle(20);     //最多空闲的jedis
        jedisPoolConfig.setMinIdle(10);     //最少有多少个空闲的jedis

        //获得jedis连接池对象
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.252.222");

        //从连接池中获得jedis
        Jedis jedis = jedisPool.getResource();
        //连接redis
//        Jedis jedis = new Jedis("192.168.252.222",6379);

        //通过jedis操作redis
        //设置key-value
        jedis.set("wtf","???");
        //通过key获得value
        String wtf = jedis.get("wtf");
        System.out.println("wtf = " + wtf);

        //关闭连接
        jedis.close();
    }
}
