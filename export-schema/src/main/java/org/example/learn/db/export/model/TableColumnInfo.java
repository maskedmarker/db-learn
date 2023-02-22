package org.example.learn.db.export.model;

public class TableColumnInfo {

    private String columnName;
    private String columnType;
    private int position;
    private String nullable;
    private String columnComment;
    private String columnDefault;

    public TableColumnInfo(int position, String columnName, String columnType, String nullable) {
        this.position = position;
        this.columnName = columnName;
        this.columnType = columnType;
        this.nullable = nullable;
    }

    public int getPosition() {
        return position;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getNullable() {
        return nullable;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    @Override
    public String toString() {
        return "TableColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", position=" + position +
                ", nullable='" + nullable + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", columnDefault='" + columnDefault + '\'' +
                '}';
    }
}
