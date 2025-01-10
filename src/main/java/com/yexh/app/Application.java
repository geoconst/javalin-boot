package com.yexh.app;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.yexh.model._MappingKit;
import com.yexh.module.test.TestController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        initActiveRecord();
        initServer().start(8888);
    }

    // 初始化javalin
    private static Javalin initServer() {
        var server = Javalin.create(config -> {
            config.fileRenderer(new JavalinJte());
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/static";
                staticFileConfig.location = Location.CLASSPATH;
            });
            config.requestLogger.http((ctx, ws) -> {
                logger.info(ctx.fullUrl());
            });
            config.jetty.modifyServer(s -> s.setStopTimeout(5_000));
        });
        server.exception(Exception.class, (e, ctx) -> {
            logger.error("global exception", e);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
        server.events(event -> {
            event.serverStopping(() -> {
                logger.info("Server is stopping");
            });
            event.serverStopped(() -> {
                logger.info("Server is stopped");
            });
        });

        routes(server);
        server.after(ctx -> {
            // 设置返回内容都是utf-8编码
            ctx.res().setCharacterEncoding(StandardCharsets.UTF_8.name());
        });
        return server;
    }

    // 注册路由
    private static void routes(Javalin app) {
        TestController.registeRoutes(app);
    }

    // 初始化数据库连接
    private static void initActiveRecord() {
        var jdbcUrl = "jdbc:mysql://localhost/games-testtable";
        HikariCpPlugin hp = new HikariCpPlugin(jdbcUrl, "root", "root");
        hp.setMaximumPoolSize(50);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(hp);
        _MappingKit.mapping(arp);

        hp.start();
        arp.start();
    }
}