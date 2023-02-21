package org.example.learn.db.export.model;

import java.util.List;

public class TableInfo {

    private String tableSchemaName;
    private String tableName;
    private List<TableColumnInfo> tableColumnInfoList;

    public TableInfo(String tableSchemaName, String tableName, List<TableColumnInfo> tableColumnInfoList) {
        this.tableSchemaName = tableSchemaName;
        this.tableName = tableName;
        this.tableColumnInfoList = tableColumnInfoList;
    }

    public String getTableSchemaName() {
        return tableSchemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<TableColumnInfo> getTableColumnInfoList() {
        return tableColumnInfoList;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableSchemaName='" + tableSchemaName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableColumnInfoList=" + tableColumnInfoList +
                '}';
    }
}
