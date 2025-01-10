package com.yexh.app;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.hikaricp.HikariCpPlugin;
import com.yexh.model._MappingKit;

import java.util.Properties;

public class DBInitor {
    // 初始化数据库连接
    public static void initActiveRecord(Properties p) {
        String jdbcurl = p.getProperty("dbjdbcurl");
        String dbuser = p.getProperty("dbuser");
        String dbpass = p.getProperty("dbpass");
        int dbpoolsize = Integer.parseInt(p.getProperty("dbpoolsize"));
        HikariCpPlugin hp = new HikariCpPlugin(jdbcurl, dbuser, dbpass);
        hp.setMaximumPoolSize(dbpoolsize);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(hp);
        _MappingKit.mapping(arp);

        hp.start();
        arp.start();
    }
}
