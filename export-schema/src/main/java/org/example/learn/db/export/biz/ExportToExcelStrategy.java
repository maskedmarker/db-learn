package org.example.learn.db.export.biz;

import org.example.learn.db.export.model.TableInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExportToExcelStrategy {

    void doExport(String filePath, List<TableInfo> tableInfos);
}
