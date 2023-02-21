package org.example.learn.db.export.biz;

import java.io.File;
import java.io.IOException;

import java.awt.*;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.example.learn.db.export.model.TableColumnInfo;
import org.example.learn.db.export.model.TableInfo;

public class ExportDbSchemaToExcel {

    public static final int ROW_NO_BACKUP = 0;
    public static final int ROW_NO_TABLE_NAME = 1;
    public static final int ROW_NO_TABLE_TITLE = 3;

    private String filePath;
    private List<TableInfo> tableInfos;

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
            // 数据库表列表sheet

            // 数据库表字段sheet
            for (int i = 0; i < tableInfos.size(); i++) {
                TableInfo tableInfo = tableInfos.get(i);
                doTableDetailSheet(workbook, i, tableInfo);
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
     * 数据库表列表
     */
    private void doTableListSheet(WritableWorkbook workbook, int index, List<TableInfo> tableInfos) throws WriteException {
        // TODO
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
        String goBackLabelName = new String("<< 返回");
        jxl.write.Label label = new jxl.write.Label(1, ROW_NO_BACKUP, goBackLabelName, cellFormat);
        sheet.addCell(label);


        //第二行 表名
        WritableCellFormat cellFormat2 = new WritableCellFormat(font);
        jxl.write.Label labelRow2 = new jxl.write.Label(1, ROW_NO_TABLE_NAME, tableInfo.getTableName(), cellFormat2);
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
        String[] tableTitle = new String[]{"序号", "字段", "中文含义", "类型", "是否可为空", "default", "说明"};
        for (int i = 0; i < tableTitle.length; i++) {
            jxl.write.Label labelTitle = new jxl.write.Label(i, ROW_NO_TABLE_TITLE, tableTitle[i], cellFormat4);
            sheet.addCell(labelTitle);
        }

        //第四行正式内容
        //数据开始的行数
        List<TableColumnInfo> tableColumnInfoList = tableInfo.getTableColumnInfoList();
        int dataRowNo = ROW_NO_TABLE_TITLE + 1;
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
