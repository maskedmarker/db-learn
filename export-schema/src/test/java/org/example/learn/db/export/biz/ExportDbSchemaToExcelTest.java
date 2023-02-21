package org.example.learn.db.export.biz;

import org.apache.commons.lang3.StringUtils;
import org.example.learn.db.export.model.TableInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ExportDbSchemaToExcelTest extends BaseDbTest{

    @Autowired
    private QueryDatabaseMetaInfo queryDatabaseMetaInfo;

    @Autowired
    private QueryTableMetaInfo queryTableMetaInfo;

    @Test
    public void testExport() throws SQLException {
        String schemaName = "emp";
//        String schemaName = "cloud";
        List<String> tableNames = queryDatabaseMetaInfo.queryAllTableNames(connection, schemaName);
        System.out.println("tableNames = " + StringUtils.join(tableNames, ","));

        List<TableInfo> tableInfoList = new ArrayList<>();
        for (String tableName : tableNames) {
            TableInfo tableInfo = queryTableMetaInfo.queryTableInfoBySql(connection, schemaName, tableName);
            System.out.println("tableInfo = " + tableInfo);
            tableInfoList.add(tableInfo);
        }

        String filename = "中邮理财恒生直销系统数据库设计说明书.xls";
        String path = "E:\\workspace\\my-git-repo\\db-learn\\" + filename;
        ExportDbSchemaToExcel exportDbSchemaToExcel = new ExportDbSchemaToExcel(path, tableInfoList);
        exportDbSchemaToExcel.export();
    }

    @Test
    public void testExport2() throws SQLException {
        String schemaName = "emp";
        List<String> tableNames = queryDatabaseMetaInfo.queryTableNames(connection,  schemaName, "","dept");
        System.out.println("tableNames = " + StringUtils.join(tableNames, ","));

        List<TableInfo> tableInfoList = new ArrayList<>();
        for (String tableName : tableNames) {
            TableInfo tableInfo = queryTableMetaInfo.queryTableInfoBySql(connection, schemaName, tableName);
            System.out.println("tableInfo = " + tableInfo);
            tableInfoList.add(tableInfo);
        }

        String filename = "中邮理财恒生直销系统数据库设计说明书.xls";
        String path = "E:\\workspace\\my-git-repo\\db-learn\\" + filename;
        ExportDbSchemaToExcel exportDbSchemaToExcel = new ExportDbSchemaToExcel(path, tableInfoList);
        exportDbSchemaToExcel.export();
    }
}