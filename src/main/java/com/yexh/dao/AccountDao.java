package com.yexh.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

public class AccountDao {

    public List<Record> findAll() {
        return Db.findAll("account");
    }

}
