package org.example.learn.db.export.biz;

import org.example.learn.db.export.config.DbConfig;
import org.example.learn.db.export.model.TableInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class QueryTableMetaInfoTest  extends BaseDbTest {

    @Autowired
    private QueryTableMetaInfo queryTableMetaInfo;

    @Test
    public void testQueryTableInfo() throws SQLException {
        String schemaName = "emp";
        String tableName = "dept";
        TableInfo tableInfo = queryTableMetaInfo.queryTableInfo(connection, schemaName, tableName);
        System.out.println("tableInfo = " + tableInfo);
    }

    @Test
    public void queryTableInfoBySql() throws SQLException {
        String schemaName = "emp";
        String tableName = "emp";
        TableInfo tableInfo = queryTableMetaInfo.queryTableInfoBySql(connection, schemaName, tableName);
        System.out.println("tableInfo = " + tableInfo);
    }
}