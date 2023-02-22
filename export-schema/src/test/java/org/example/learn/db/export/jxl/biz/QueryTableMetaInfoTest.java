package org.example.learn.db.export.jxl.biz;

import org.example.learn.db.export.jxl.model.TableInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

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