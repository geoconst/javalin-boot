package com.yexh.app;

import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        Properties properties = Config.initConfig();
        DBInitor.initActiveRecord(properties);
        int port = Integer.parseInt(properties.getProperty("port"));
        ServerInitor.initServer().start(port);
    }
}