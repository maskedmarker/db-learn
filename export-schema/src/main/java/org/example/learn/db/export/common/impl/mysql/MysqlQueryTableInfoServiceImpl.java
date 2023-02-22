package org.example.learn.db.export.common.impl.mysql;

import org.example.learn.db.export.common.QueryTableInfoService;
import org.example.learn.db.export.constant.MysqlConstants;
import org.example.learn.db.export.exception.DbExportBaseException;
import org.example.learn.db.export.model.TableColumnInfo;
import org.example.learn.db.export.model.TableInfo;
import org.example.learn.db.export.util.DbUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("mysqlQueryTableInfoServiceImpl")
public class MysqlQueryTableInfoServiceImpl implements QueryTableInfoService {
    @Override
    public List<TableInfo> queryAll(Connection connection, String schema) {
        List<String> allTableNames = queryAllTableName(connection, schema);

        List<TableInfo> all = new ArrayList<>();
        for (String tableName : allTableNames) {
            all.add(query(connection, schema, tableName));
        }

        return all;
    }

    @Override
    public TableInfo query(Connection connection, String schema, String tableName) {
        return queryTableInfo(connection, schema, tableName);
    }

    private List<String> queryAllTableName(Connection connection, String schema) {
        try {
            return doQueryAllTableNameBySql(connection, schema);
        } catch (SQLException e) {
            throw new DbExportBaseException(e);
        }
    }

    private List<String> doQueryAllTableNameBySql(Connection connection, String schema) throws SQLException {
        String tableQuerySql = String.format("select t.TABLE_SCHEMA, t.TABLE_NAME, t.TABLE_COMMENT from information_schema.TABLES t where t.TABLE_SCHEMA  = '%s' order by t.TABLE_NAME",
                schema);
        Statement statement = connection.createStatement();
        ResultSet tableResultSet = statement.executeQuery(tableQuerySql);
        List<Map<String, Object>> rowList = DbUtils.getResult(tableResultSet);

        List<String> tableNames = rowList.stream()
                .map(i -> (String) i.get(MysqlConstants.DB_META_KEY_TABLE_NAME))
                .collect(Collectors.toList());
        return tableNames;

    }

    private TableInfo queryTableInfo(Connection connection, String schemaName, String tableName) {
        try {
            return doQueryTableInfoBySql(connection, schemaName, tableName);
        } catch (SQLException e) {
            throw new DbExportBaseException(e);
        }
    }


    private TableInfo doQueryTableInfoBySql(Connection connection, String schemaName, String tableName) throws SQLException {
        String queryTableSql = String.format("select t.TABLE_SCHEMA, t.TABLE_NAME, t.TABLE_COMMENT from information_schema.TABLES t where t.TABLE_SCHEMA = '%s' and t.TABLE_NAME = '%s'",
                schemaName, tableName);
        Statement statement = connection.createStatement();
        ResultSet tableResultSet = statement.executeQuery(queryTableSql);
        List<Map<String, Object>> tableRowList = DbUtils.getResult(tableResultSet);
        TableInfo tableInfo = constructTableInfo(tableRowList.get(0));

        String queryColumnSql = String.format("select * from information_schema.`COLUMNS` c  where c.TABLE_SCHEMA = '%s' and c.TABLE_NAME  = '%s' order by c.ORDINAL_POSITION ",
                schemaName, tableName);
        statement = connection.createStatement();
        ResultSet columnResultSet = statement.executeQuery(queryColumnSql);
        List<Map<String, Object>> columnRowList = DbUtils.getResult(columnResultSet);
        List<TableColumnInfo> tableColumnInfos = constructTableColumnInfo(columnRowList);
        tableInfo.setTableColumnInfoList(tableColumnInfos);
        return tableInfo;
    }

    private List<TableColumnInfo> constructTableColumnInfo(List<Map<String, Object>> rowList) {
        List<TableColumnInfo> tableColumnInfos = rowList.stream()
                .map(i -> {
                    String columnName = (String) i.get("COLUMN_NAME");
                    String columnType = (String) i.get("COLUMN_TYPE");
                    int position = (Integer) i.get("ORDINAL_POSITION");
                    String nullable = (String) i.get("IS_NULLABLE");
                    String columnComment = (String) i.get("COLUMN_COMMENT");
                    String columnDefault = (String) i.get("COLUMN_DEFAULT");
                    TableColumnInfo tableColumnInfo = new TableColumnInfo(position, columnName, columnType, nullable);
                    tableColumnInfo.setColumnComment(columnComment);
                    tableColumnInfo.setColumnDefault(columnDefault);
                    return tableColumnInfo;
                })
                .collect(Collectors.toList());
        return tableColumnInfos;
    }

    private TableInfo constructTableInfo(Map<String, Object> row) {
        String tableSchemaName = (String) row.get("TABLE_SCHEMA");
        String tableName = (String) row.get("TABLE_NAME");
        String tableComment = (String) row.get("TABLE_COMMENT");
        TableInfo tableInfo = new TableInfo(tableSchemaName, tableName, null);
        tableInfo.setTableComment(tableComment);
        return tableInfo;
    }
}
