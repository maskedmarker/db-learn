package org.example.learn.db.export.biz.impl.strategy;

import org.example.learn.db.export.constant.TableTypEnum;
import org.example.learn.db.export.util.DbUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Deprecated
public class QueryDatabaseMetaInfo {

    public static final String DB_META_KEY_TABLE_CATALOG = "TABLE_CAT";
    public static final String DB_META_KEY_TABLE_TYPE = "TABLE_TYPE";
    public static final String DB_META_KEY_TABLE_NAME = "TABLE_NAME";

    public List<String> queryDatabaseNames(Connection connection) throws SQLException {
        // 获取当前用户可用的所有数据库名
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet catalogs = databaseMetaData.getCatalogs();
        List<Map<String, Object>> rowList = DbUtils.getResult(catalogs);

        List<String> dbNames = rowList.stream()
                .map(i -> (String) i.get(DB_META_KEY_TABLE_CATALOG))
                .collect(Collectors.toList());
        return dbNames;
    }

    public List<String> queryTableTypes(Connection connection) throws SQLException {
        // 获取当前用户可用的所有数据库名
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet tableTypesResultSet = databaseMetaData.getTableTypes();
        List<Map<String, Object>> rowList = DbUtils.getResult(tableTypesResultSet);

        List<String> tableTypes = rowList.stream()
                .map(i -> (String) i.get(DB_META_KEY_TABLE_TYPE))
                .collect(Collectors.toList());

        return tableTypes;
    }

    private List<String> queryTableNames(Connection connection, String catalog, String schemaPattern, String tableNamePattern, String[] tableTypes) throws SQLException {
        // 获取当前用户可用的所有数据库名
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet tableResultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, tableTypes);
        List<Map<String, Object>> rowList = DbUtils.getResult(tableResultSet);

        List<String> tableNames = rowList.stream()
                .map(i -> (String) i.get(DB_META_KEY_TABLE_NAME))
                .collect(Collectors.toList());
        return tableNames;
    }

    public List<String> queryTableNames(Connection connection, String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        String[] tableTypes = new String[]{TableTypEnum.TABLE.getCode()};
        return queryTableNames(connection, catalog, schemaPattern, tableNamePattern, tableTypes);
    }

    public List<String> queryAllTableNames(Connection connection, String schema) throws SQLException {
        return queryTableNames(connection,  schema, "","");
    }
}
