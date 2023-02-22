package org.example.learn.db.export.jxl.biz;

import org.apache.commons.lang3.StringUtils;
import org.example.learn.db.export.jxl.model.TableInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExportDbSchemaToExcelTest extends BaseDbTest{

    @Autowired
    private QueryDatabaseMetaInfo queryDatabaseMetaInfo;

    @Autowired
    private QueryTableMetaInfo queryTableMetaInfo;

    @Test
    public void testExport() throws SQLException {
        String schemaName = "lcpt_trans";
        List<String> tableNames = queryDatabaseMetaInfo.queryAllTableNames(connection, schemaName);
        System.out.println("tableNames = " + StringUtils.join(tableNames, ","));
        tableNames = tableNames.subList(0, 30);

        List<TableInfo> tableInfoList = new ArrayList<>();
        for (String tableName : tableNames) {
            TableInfo tableInfo = queryTableMetaInfo.queryTableInfoBySql(connection, schemaName, tableName);
            tableInfoList.add(tableInfo);
        }
        System.out.println("tableInfoList.size() = " + tableInfoList.size());

        String filename = "中邮理财恒生直销系统数据库设计说明书-test.xls";
        String path = System.getProperty("user.dir") + File.separator + filename;
        ExportDbSchemaToExcel exportDbSchemaToExcel = new ExportDbSchemaToExcel(path, tableInfoList);
        exportDbSchemaToExcel.export();
    }
}