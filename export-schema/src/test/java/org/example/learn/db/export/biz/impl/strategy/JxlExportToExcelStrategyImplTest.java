package org.example.learn.db.export.biz.impl.strategy;

import org.example.learn.db.export.BaseDbTest;
import org.example.learn.db.export.biz.ExportToExcelStrategy;
import org.example.learn.db.export.common.QueryTableInfoService;
import org.example.learn.db.export.model.TableInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class JxlExportToExcelStrategyImplTest extends BaseDbTest {

    @Autowired
    @Qualifier("mysqlQueryTableInfoServiceImpl")
    private QueryTableInfoService queryTableInfoService;

    @Autowired
    @Qualifier("jxlExportToExcelStrategyImpl")
    private ExportToExcelStrategy exportToExcelStrategy;

    @Test
    public void testDoExport() {
        String schema = "lcpt_trans1";
        List<TableInfo> all = queryTableInfoService.queryAll(connection, schema);
        System.out.println("all.size() = " + all.size());

        String filename = "中邮理财恒生直销系统数据库设计说明书-test.xls";
        String path = System.getProperty("user.dir") + File.separator + filename;
        exportToExcelStrategy.doExport(path, all);
    }
}