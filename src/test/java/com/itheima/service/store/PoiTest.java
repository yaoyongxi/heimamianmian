package com.itheima.service.store;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class PoiTest {
    @Test
    public void testWriteByPoi() throws IOException{
        //1.获取到对应的Excel文件，工作簿文件
        Workbook wb = new XSSFWorkbook();
        //2.创建工作表
        Sheet sheet = wb.createSheet();
        sheet = wb.createSheet("测试表");
        //3.创建工作表中的行对象
        Row row = sheet.createRow(1);
        //4.创建工作表中行中的列对象
        Cell cell = row.createCell(1);
        //5.在列中写入数据
        cell.setCellValue("测试单元格");

        //创建一个文件对象
        File f = new File("test.xlsx");
        OutputStream os = new FileOutputStream(f);
        wb.write(os);
        wb.close();
        os.close();

    }
    @Test
    public void testReadByPoi() throws IOException{
        //1.获取要读取的文件工作簿对象
        Workbook wb = new XSSFWorkbook("test.xlsx");
        ///2.获取工作表
        Sheet s = wb.getSheetAt(1);
        //获取行
        Row row = s.getRow(1);
        //4.获取列
        Cell cell = row.getCell(1);
        //5.根据数据类型获取数据
        String data = cell.getStringCellValue();
        System.out.println(data);
        wb.close();
    }
    @Test
    public void testProjectPoi() throws IOException{
        //1.获取到对应的Excel文件，工作簿文件
        Workbook wb = new XSSFWorkbook();
        //2.创建工作表
        Sheet s = wb.createSheet("题目数据文件");

        //制作标题
        s.addMergedRegion(new CellRangeAddress(1,1,1,12));
        Row row_1 = s.createRow(1);
        Cell cell_1_11 = row_1.createCell(1);
        cell_1_11.setCellValue("在线试题导出信息");
        //创建一个样式
        CellStyle cs  = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cell_1_11.setCellStyle(cs);

        //制作标题
        String[] fields = {"题目ID","所属公司ID","所属目录ID","题目简介","题干描述","题干配图","题目分析","题目类型","题目难度","是否经典题","题目状态","审核状态"};
        Row row_2 = s.createRow(2);
        for (int cell = 1; cell <=fields.length; cell++) {
            Cell cell_2_temp = row_2.createCell(cell);
            cell_2_temp.setCellValue(fields[cell-1]);
            //创建一个样式
            CellStyle cs_field  = wb.createCellStyle();
            cs_field.setAlignment(HorizontalAlignment.CENTER);
            cs_field.setVerticalAlignment(VerticalAlignment.CENTER);
            cell_2_temp.setCellStyle(cs_field);
        }

        //制作数据区

        //创建一个文件对象
        File f = new File("test.xlsx");
        OutputStream os = new FileOutputStream(f);
        wb.write(os);
        wb.close();
        os.close();
    }
}
