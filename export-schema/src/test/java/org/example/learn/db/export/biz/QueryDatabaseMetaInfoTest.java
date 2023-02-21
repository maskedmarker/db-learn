package org.example.learn.db.export.biz;

import org.apache.commons.lang3.StringUtils;
import org.example.learn.db.export.config.DbConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class QueryDatabaseMetaInfoTest extends BaseDbTest{

    @Autowired
    private QueryDatabaseMetaInfo queryDatabaseMetaInfo;

    @Test
    public void testQueryDatabase() throws Exception {
        List<String> databases = queryDatabaseMetaInfo.queryDatabaseNames(connection);
        System.out.println("all databases = \r\n" + StringUtils.join(databases, "\r\n"));


    }

    @Test
    public void queryTableTypes() throws SQLException {
        List<String> allTableTypes = queryDatabaseMetaInfo.queryTableTypes(connection);
        System.out.println("allTableTypes = " + StringUtils.join(allTableTypes, ","));
    }

    @Test
    public void queryAllTableNames() throws SQLException {
        String schema = "emp";
        List<String> tableNames = queryDatabaseMetaInfo.queryAllTableNames(connection, schema);
        System.out.println("tableNames = " + StringUtils.join(tableNames, ","));
    }
}