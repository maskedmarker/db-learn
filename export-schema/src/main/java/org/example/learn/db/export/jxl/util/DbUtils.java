package org.example.learn.db.export.jxl.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtils {

    public static List<Map<String, Object>> getResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        List<Map<String, Object>> rows = new ArrayList<>();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnLabel = metaData.getColumnLabel(i);
                int columnType = metaData.getColumnType(i);
                Object colValue = getValue(resultSet, columnLabel, columnType);
                rowData.put(columnLabel, colValue);
            }

            rows.add(rowData);
        }

        return rows;
    }

    public static Object getValue(ResultSet resultSet, String columnLabel, int columnType) throws SQLException {
        Object value = null;
        switch (columnType) {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                value = resultSet.getString(columnLabel);
                break;
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
                value = resultSet.getInt(columnLabel);
                break;
            default:
                throw new RuntimeException("column type: " + columnType + " not supported");
        }

        return value;
    }
}
