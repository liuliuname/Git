package com.example.seckill.seckill.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    public static JedisPool getPool() {
        // 简单创建 Jedis的方法：
        // final Jedis jedis = new Jedis("127.0.0.1",6379);

        // 下边使用线程池的方案：jedis对象是线程不安全的，因此在并发情况下要使用JedisPool,默认情况下jedisPool只支持8个连接，因此在声明JedisPool时要先修改JedisPool的最大连接数
        JedisPoolConfig config = new JedisPoolConfig();
        // 修改最大连接数
        config.setMaxTotal(32);

        // 声明一个线程池
        JedisPool pool = new JedisPool(config, "10.1.102.166", 6379);

        return pool;
    }
}