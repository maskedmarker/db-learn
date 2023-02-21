package org.example.learn.db.export.biz;

import org.example.learn.db.export.model.TableColumnInfo;
import org.example.learn.db.export.model.TableInfo;
import org.example.learn.db.export.util.DbUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QueryTableMetaInfo {

    public TableInfo queryTableInfo(Connection connection, String schemaName, String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet columnResultSet = databaseMetaData.getColumns(schemaName,null, tableName, null);
        List<Map<String, Object>> rowList = DbUtils.getResult(columnResultSet);
        List<TableColumnInfo> tableColumnInfos = constructTableColumnInfo(rowList);
        return new TableInfo(schemaName, tableName, tableColumnInfos);
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

    public TableInfo queryTableInfoBySql(Connection connection, String schemaName, String tableName) throws SQLException {
        String querySql = String.format("select * " +
                "from information_schema.`COLUMNS` c " +
                "where c.TABLE_SCHEMA = '%s' " +
                "  and c.TABLE_NAME  = '%s' " +
                "order by c.ORDINAL_POSITION ", schemaName, tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(querySql);
        List<Map<String, Object>> rowList = DbUtils.getResult(resultSet);
        List<TableColumnInfo> tableColumnInfos = constructTableColumnInfo(rowList);
        return new TableInfo(schemaName, tableName, tableColumnInfos);
    }
}
