package org.example.learn.db.export.biz.impl.strategy;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.format.*;
import jxl.write.Label;
import jxl.write.*;
import org.example.learn.db.export.model.TableColumnInfo;
import org.example.learn.db.export.model.TableInfo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Deprecated
public class ExportDbSchemaToExcel {


    public static final int INFO_SHEET_NO = 0;
    // 文档信息sheet

    // 表列表sheet
    public static final int LIST_SHEET_NO = INFO_SHEET_NO + 1;
    public static final int LIST_ROW_NO_BACKUP = 0;
    public static final int LIST_ROW_NO_TABLE_TITLE = LIST_ROW_NO_BACKUP + 1;
    public static final int LIST_COLUMN_NO_TABLE_NAME = 2;


    // 表详情起始页
    public static final int DETAIL_ROW_NO_BACKUP = 0;
    public static final int DETAIL_SHEET_NO_START = LIST_SHEET_NO + 1;
    public static final int DETAIL_ROW_NO_TABLE_NAME = 1;
    public static final int DETAIL_ROW_NO_TABLE_TITLE = 3;

    private String filePath;
    private List<TableInfo> tableInfos;

    private int lastSheetNo = 0;

    public ExportDbSchemaToExcel(String filePath, List<TableInfo> tableInfos) {
        this.filePath = filePath;
        this.tableInfos = tableInfos;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<TableInfo> getTableInfos() {
        return tableInfos;
    }

    public void export() {
        try {
            // 创建写工作簿对象
            File file = new File(this.filePath);
            int sheetNo = 0;
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            // 添加文档信息sheet
            lastSheetNo = INFO_SHEET_NO;
            doDocInfoSheet(workbook, lastSheetNo, "文档信息");

            // 添加数据库表列表sheet
            lastSheetNo = LIST_SHEET_NO;
            doTableListSheet(workbook, lastSheetNo, "数据表", tableInfos);

            // 添加数据库表字段sheet
            lastSheetNo = DETAIL_SHEET_NO_START;
            for (int i = 0; i < tableInfos.size(); i++) {
                TableInfo tableInfo = tableInfos.get(i);
                doTableDetailSheet(workbook, lastSheetNo + i, tableInfo);
            }

            //开始执行写入操作
            workbook.write();
            //关闭流
            workbook.close();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文档信息sheet
     */
    private void doDocInfoSheet(WritableWorkbook workbook, int index, String sheetLabel) throws WriteException {
        // TODO
        WritableSheet sheet = workbook.createSheet(sheetLabel, index);
    }

    /**
     * 数据库表列表
     */
    private void doTableListSheet(WritableWorkbook workbook, int index, String sheetLabel, List<TableInfo> tableInfos) throws WriteException {
        // TODO
        WritableSheet sheet = workbook.createSheet(sheetLabel, index);

        //第一行 固定 “返回”字样
        String goBackLabelName = "<< 返回首页";
        WritableHyperlink goBackHyperlink = new WritableHyperlink(1, DETAIL_ROW_NO_BACKUP, goBackLabelName, workbook.getSheet(INFO_SHEET_NO), 0, 0);
        sheet.addHyperlink(goBackHyperlink);

        //固定标题
        String[] tableHeads = new String[]{"系统编号", "类别", "表名", "中文名称", "英文名称", "功能描述"};
        // 标题样式
        WritableCellFormat cellFormat4 = new WritableCellFormat(getFont());
        // 设置自动换行;
        cellFormat4.setWrap(false);
        // 设置边框;
        cellFormat4.setBorder(Border.ALL, BorderLineStyle.THIN);
        // 设置文字居中对齐方式;
        cellFormat4.setAlignment(Alignment.CENTRE);
        // 设置垂直居中;
        cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);
//        cellFormat4.setBackground(Colour.OCEAN_BLUE);

        for (int i = 0; i < tableHeads.length; i++) {
            Label labelTitle = new Label(i, LIST_ROW_NO_TABLE_TITLE, tableHeads[i], cellFormat4);
//            Label labelTitle = new Label(i, LIST_ROW_NO_TABLE_TITLE, tableHeads[i]);
            sheet.addCell(labelTitle);
        }

        int dataRowNo = LIST_ROW_NO_TABLE_TITLE + 1;
        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);

            int columnIndexNo = 0;
            Label col0 = new Label(columnIndexNo++, dataRowNo, Integer.toString(i + 1), cellFormat4);
            Label col1 = new Label(columnIndexNo++, dataRowNo, "", cellFormat4);
            Label col2 = new Label(columnIndexNo++, dataRowNo, tableInfo.getTableName(), cellFormat4);
            Label col3 = new Label(columnIndexNo++, dataRowNo, tableInfo.getTableComment(), cellFormat4);
            Label col4 = new Label(columnIndexNo++, dataRowNo, tableInfo.getTableName(), cellFormat4);
            Label col5 = new Label(columnIndexNo++, dataRowNo, tableInfo.getTableComment(), cellFormat4);
            sheet.addCell(col0);
            sheet.addCell(col1);
            sheet.addCell(col2);
            sheet.addCell(col3);
            sheet.addCell(col4);
            sheet.addCell(col5);

            dataRowNo++;
        }
    }

    /**
     * 数据库表字段
     */
    private void doTableDetailSheet(WritableWorkbook workbook, int index, TableInfo tableInfo) throws WriteException {
        WritableSheet sheet = workbook.createSheet(tableInfo.getTableName(), index);
        WritableFont font = getFont();
        // 工作表
        /**
         * 	<< 返回
         * 	tbassetacc
         *
         * 序号	字段	中文含义	类型	是否可为空	default	说明    --tabletitle
         * 1	in_client_no	内部客户编号	VARCHAR2(20)	NO	无
         */

        // 创建样式
        // 设置背景颜色;
        Color color = Color.decode("#cfcfc4"); // 自定义的颜色
        workbook.setColourRGB(Colour.LIGHT_TURQUOISE, color.getRed(), color.getGreen(), color.getBlue());
        WritableCellFormat cellFormat = getCellFormat(font);
        // 给sheet电子版中设置列的宽度;
        //sheet.getSettings().setDefaultColumnWidth(14);
        sheet.setColumnView(1, 30);


        //第一行 固定 “返回”字样
        String goBackLabelName = "<< 返回";
//        Label label = new Label(1, ROW_NO_BACKUP, goBackLabelName, cellFormat);
//        sheet.addCell(label);
        WritableHyperlink goBackHyperlink = new WritableHyperlink(1, DETAIL_ROW_NO_BACKUP, goBackLabelName, workbook.getSheet(LIST_SHEET_NO), 0, 0);
        sheet.addHyperlink(goBackHyperlink);

        //第二行 表名
        WritableCellFormat cellFormat2 = new WritableCellFormat(font);
        Label labelRow2 = new Label(1, DETAIL_ROW_NO_TABLE_NAME, tableInfo.getTableName(), cellFormat2);
        sheet.addCell(labelRow2);


        //第三行空
        //第四行标题
        WritableCellFormat cellFormat4 = new WritableCellFormat(font);
        // 设置自动换行;
        cellFormat4.setWrap(true);
        // 设置边框;
        cellFormat4.setBorder(Border.ALL, BorderLineStyle.THIN);
        // 设置文字居中对齐方式;
        cellFormat4.setAlignment(Alignment.CENTRE);
        // 设置垂直居中;
        cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);
        //固定标题
        String[] tableHeads = new String[]{"序号", "字段", "中文含义", "类型", "是否可为空", "default", "说明"};
        for (int i = 0; i < tableHeads.length; i++) {
            Label labelTitle = new Label(i, DETAIL_ROW_NO_TABLE_TITLE, tableHeads[i], cellFormat4);
            sheet.addCell(labelTitle);
        }

        //第四行正式内容
        //数据开始的行数
        List<TableColumnInfo> tableColumnInfoList = tableInfo.getTableColumnInfoList();
        int dataRowNo = DETAIL_ROW_NO_TABLE_TITLE + 1;
        for (int i = 0; i < tableColumnInfoList.size(); i++) {
            TableColumnInfo tableColumnInfo = tableColumnInfoList.get(i);

            int columnIndexNo = 0;
            Label col0 = new Label(columnIndexNo++, dataRowNo, Integer.toString(tableColumnInfo.getPosition()), cellFormat4);
            Label col1 = new Label(columnIndexNo++, dataRowNo, tableColumnInfo.getColumnName(), cellFormat4);
            Label col2 = new Label(columnIndexNo++, dataRowNo, "", cellFormat4);
            Label col3 = new Label(columnIndexNo++, dataRowNo, tableColumnInfo.getColumnType(), cellFormat4);
            Label col4 = new Label(columnIndexNo++, dataRowNo, tableColumnInfo.getNullable(), cellFormat4);
            Label col5 = new Label(columnIndexNo++, dataRowNo, tableColumnInfo.getColumnDefault(), cellFormat4);
            Label col6 = new Label(columnIndexNo++, dataRowNo, tableColumnInfo.getColumnComment(), cellFormat4);
            sheet.addCell(col0);
            sheet.addCell(col1);
            sheet.addCell(col2);
            sheet.addCell(col3);
            sheet.addCell(col4);
            sheet.addCell(col5);
            sheet.addCell(col6);

            dataRowNo++;
        }

        // todo 根据表名,为数据表添加指向当前sheet的超级链接
        addHyperLinkForSheet(workbook.getSheet(LIST_SHEET_NO), sheet, tableInfo.getTableName());
    }

    private void addHyperLinkForSheet(WritableSheet listSheet, WritableSheet detailSheet, String tableName) {
        for (int i = 0; i < tableInfos.size(); i++) {
            int rowNo = LIST_ROW_NO_TABLE_TITLE + i;
            Cell tableNameCell = listSheet.getCell(LIST_COLUMN_NO_TABLE_NAME, rowNo);
            if (tableName.equals(tableNameCell.getContents())) {
                // todo 写入链接还是附带链接???
            }
        }
    }

    private WritableFont getFont() {
        WritableFont font = new WritableFont(WritableFont.createFont("宋体"),
                10,
                WritableFont.BOLD,
                false,
                UnderlineStyle.NO_UNDERLINE,
                Colour.BLACK);
        return font;
    }

    private WritableCellFormat getCellFormat(WritableFont font) throws WriteException {
        WritableCellFormat cellFormat = new WritableCellFormat(font);
        cellFormat.setBackground(Colour.LIGHT_TURQUOISE);
        // 设置边框;
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        // 设置文字居中对齐方式;
        cellFormat.setAlignment(Alignment.CENTRE);
        // 设置垂直居中;
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        return cellFormat;
    }
}
