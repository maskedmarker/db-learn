package org.example.learn.db.export.biz.impl;

import org.example.learn.db.export.biz.ExportDbSchemaToExcelService;
import org.example.learn.db.export.biz.ExportToExcelStrategy;
import org.example.learn.db.export.common.QueryTableInfoService;
import org.example.learn.db.export.config.DbConfig;
import org.example.learn.db.export.exception.DbExportBaseException;
import org.example.learn.db.export.model.TableInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Component
public class ExportDbSchemaToExcelServiceImpl implements ExportDbSchemaToExcelService {

    @Autowired
    private DbConfig dbConfig;

    @Autowired
    private QueryTableInfoService queryTableInfoService;

    @Autowired
    private ExportToExcelStrategy exportToExcelStrategy;

    @Override
    public void export(String filePath, String schema) {
        Connection connection = null;
        try {
            Class.forName(dbConfig.getDriver());
            connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
            List<TableInfo > tableInfos = queryTableInfoService.queryAll(connection, schema);
            exportToExcelStrategy.doExport(filePath, tableInfos);
        } catch (Exception e) {
            throw new DbExportBaseException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    // ignore
                }
            }
        }
    }
}
