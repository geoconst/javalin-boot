package com.yexh.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    public static Properties initConfig() {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("app.properties")) {
            // 使用properties对象加载输入流
            properties.load(in);
            return properties;
        } catch (Exception e) {
            logger.error("Load config proerties file failed!", e);
            System.exit(-1);
            return null;
        }
    }
}
