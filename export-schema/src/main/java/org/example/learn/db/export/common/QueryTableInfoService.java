package org.example.learn.db.export.common;

import org.example.learn.db.export.model.TableInfo;

import java.sql.Connection;
import java.util.List;

public interface QueryTableInfoService {

    List<TableInfo> queryAll(Connection connection, String schema);

    TableInfo query(Connection connection, String schema, String tableName);
}
