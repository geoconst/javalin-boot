package com.yexh.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Properties properties = Config.initConfig();
        DB.initActiveRecord(properties);
        int port = Integer.parseInt(properties.getProperty("port"));
        Server.initServer().start(port);
    }
}