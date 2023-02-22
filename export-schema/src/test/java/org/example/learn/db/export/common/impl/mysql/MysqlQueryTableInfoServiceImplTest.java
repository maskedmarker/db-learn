package org.example.learn.db.export.common.impl.mysql;

import org.example.learn.db.export.BaseDbTest;
import org.example.learn.db.export.common.QueryTableInfoService;
import org.example.learn.db.export.model.TableInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.Assert.*;

public class MysqlQueryTableInfoServiceImplTest extends BaseDbTest {

    @Autowired
    @Qualifier("mysqlQueryTableInfoServiceImpl")
    private QueryTableInfoService queryTableInfoService;


    @Test
    public void queryAll() {
        String schema = "lcpt_trans1";
        List<TableInfo> all = queryTableInfoService.queryAll(connection, schema);
        System.out.println("all.size() = " + all.size());
    }

    @Test
    public void query() {
    }
}