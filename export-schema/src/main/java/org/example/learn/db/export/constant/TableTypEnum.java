package org.example.learn.db.export.constant;

public enum TableTypEnum {

//    LOCAL_TEMPORARY("LOCAL TEMPORARY", ""),
//    SYSTEM_TABLE("SYSTEM TABLE", ""),
//    SYSTEM_VIEW("LOCAL TEMPORARY", ""),
    TABLE("TABLE", "TABLE"),
    VIEW("VIEW", "VIEW");

    private String code;
    private String description;

    TableTypEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
