package org.example.learn.db.export.model;

import java.util.List;

public class TableInfo {

    private String tableSchemaName;
    private String tableName;
    private List<TableColumnInfo> tableColumnInfoList;
    private String tableComment;

    public TableInfo() {
    }

    public TableInfo(String tableSchemaName, String tableName, List<TableColumnInfo> tableColumnInfoList) {
        this.tableSchemaName = tableSchemaName;
        this.tableName = tableName;
        this.tableColumnInfoList = tableColumnInfoList;
    }

    public String getTableSchemaName() {
        return tableSchemaName;
    }

    public void setTableSchemaName(String tableSchemaName) {
        this.tableSchemaName = tableSchemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableColumnInfo> getTableColumnInfoList() {
        return tableColumnInfoList;
    }

    public void setTableColumnInfoList(List<TableColumnInfo> tableColumnInfoList) {
        this.tableColumnInfoList = tableColumnInfoList;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableSchemaName='" + tableSchemaName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableColumnInfoList=" + tableColumnInfoList +
                ", tableComment='" + tableComment + '\'' +
                '}';
    }
}
