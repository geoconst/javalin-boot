package com.yexh.app;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Connection;
import redis.clients.jedis.JedisPooled;

import java.util.Properties;

public class RedisInitor {
    public static JedisPooled init(Properties p) {
        String redishost = p.getProperty("redishost");
        int redisport = Integer.parseInt(p.getProperty("redisport"));
        GenericObjectPoolConfig<Connection> poolConfig = new GenericObjectPoolConfig<>();
        return new JedisPooled(poolConfig, redishost, redisport);
    }
}
