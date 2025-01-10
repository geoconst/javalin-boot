package com.yexh.app;

import redis.clients.jedis.JedisPooled;

import java.util.Properties;

public class App {
    public static Properties config;
    public static JedisPooled jedis;

    public static void main(String[] args) {
        App.config = Config.initConfig();
        DBInitor.initActiveRecord(config);
        App.jedis = RedisInitor.init(config);
        int port = Integer.parseInt(config.getProperty("port"));
        ServerInitor.initServer().start(port);
    }
}