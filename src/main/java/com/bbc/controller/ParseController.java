package com.bbc.controller;

import com.bbc.common.ExcelUtils;
import com.bbc.common.POIUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ParseController
 * @Author WangHaoWei
 * @Package com.bbc.controller
 * @Date 2023/10/11 17:38
 * @description: 解析excel文件
 */
@RestController
public class ParseController {

    @PostMapping("testExport")
    public ResponseEntity parse(HttpServletResponse response,String fileName) throws IOException {
        ExcelUtils.setExcelResponse(response, fileName);
        /*XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("Hello World");
        XSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("This is a test");
        XSSFCell cell3 = row.createCell(2);
        cell3.setCellValue("This is another test");
        XSSFCell cell4 = row.createCell(5);
        cell4.setCellValue("This is the last test");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));*/
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFCellStyle thinkCellStyle = workbook.createCellStyle();
        thinkCellStyle.setBorderTop(BorderStyle.THICK);
        thinkCellStyle.setBorderBottom(BorderStyle.THICK);
        thinkCellStyle.setBorderLeft(BorderStyle.THICK);
        thinkCellStyle.setBorderRight(BorderStyle.THICK);
        thinkCellStyle.setAlignment(HorizontalAlignment.CENTER);
        thinkCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFSheet sheet = workbook.createSheet(fileName);
        //设置表头
        List<String> headers = new ArrayList<>();
        headers.add("气区");
        headers.add("积液程度");
        headers.add("井次");
        headers.add("积液高度");
        headers.add("平均积液高度");
        List<String> headers2 = new ArrayList<>();
        headers2.add("气区");
        headers2.add("测试砂面井数");
        headers2.add("测试出砂井数");
        headers2.add("测试井平均砂面高度");
        headers2.add("测试井平均砂面比例");
        headers2.add("冲砂井数");
        headers2.add("冲前平均砂面高度");
        headers2.add("冲前平均砂埋比例");
        headers2.add("冲后平均砂面高度");
        headers2.add("冲后平均砂埋比例");
        List<String> headers3 = new ArrayList<>();
        headers3.add("气区");
        headers3.add("出砂井数");
        headers3.add("冲前平均砂面高度");
        headers3.add("冲前平均砂面比例");
        headers3.add("出砂井数");
        headers3.add("冲后平均砂面高度");
        headers3.add("冲后平均砂埋比例");
        XSSFRow headRow1 = sheet.createRow(1);
        for (int j = headers.size() + 1; j < headers.size() + headers2.size() + 1; j++) {
            XSSFCell cell = headRow1.createCell(j);
            cell.setCellStyle(thinkCellStyle);
            cell.setCellValue(headers2.get(j - headers.size() - 1));
        }
        for (int j = headers.size() + headers2.size() +2; j < headers.size() + headers2.size() +headers3.size()+ 2; j++) {
            XSSFCell cell = headRow1.createCell(j);
            cell.setCellStyle(thinkCellStyle);
            cell.setCellValue(headers3.get(j- headers2.size() - headers.size() - 2));
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        return ResponseEntity.ok("导出成功");
    }
}
