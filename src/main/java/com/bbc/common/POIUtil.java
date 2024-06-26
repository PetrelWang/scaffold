package com.bbc.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: POIUtil
 * @Author WangHaoWei
 * @Package com.bbc.common
 * @Date 2023/10/11 17:37
 * @description: poi工具类
 */
public class POIUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";


    public static List<String[]> readExcel(MultipartFile file) throws IOException {

        // 检查文件

        Boolean checkfile = checkFile(file);
        if (!checkfile) {
            return new ArrayList<>();
        }
        // 获得Workbook工作薄对象

        Workbook workbook = getWorkBook(file);

        // 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回

        List<String[]> list = new ArrayList<String[]>();

        if (workbook != null) {

            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) { // 获得当前sheet工作表

                Sheet sheet = workbook.getSheetAt(sheetNum);

                if (sheet == null) {

                    continue;

                }

                // 获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                // 获得当前sheet的结束行

                int lastRowNum = sheet.getLastRowNum();

                // 循环所有行
                for (int rowNum = firstRowNum ; rowNum <= lastRowNum; rowNum++) {
                    // 获得当前行
                    Row row = sheet.getRow(rowNum);

                    if (row == null) {

                        continue;

                    }

                    // 获得当前行的开始列

                    int firstCellNum = row.getFirstCellNum();

                    // 获得当前行的列数

                    int lastCellNum = row.getPhysicalNumberOfCells();

                    String[] cells = new String[row.getPhysicalNumberOfCells()];

                    // 循环当前行

                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {

                        Cell cell = row.getCell(cellNum);

                        cells[cellNum] = getCellValue(cell);

                    }

                    list.add(cells);

                }

            }
//             workbook.close();

        }

        return list;

    }

    public static Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();

        // 创建Workbook工作薄对象，表示整个excel

        Workbook workbook = null;

        try {

            // 获取excel文件的io流
            InputStream is = file.getInputStream();

            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)) { // 2003

                workbook = new HSSFWorkbook(is);

            } else if (fileName.endsWith(xlsx)) { // 2007 及2007以上

                workbook = new XSSFWorkbook(is);

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return workbook;

    }


    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                // 数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                if(!StringUtils.isEmpty(cellValue)&&cellValue.contains("\n")){
                    //删除换行符
                    int i = cellValue.indexOf("\n");
                    cellValue = cellValue.substring(0, i);
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }

        return cellValue;
    }


    public static boolean checkFile(MultipartFile file) {
        // 判断文件是否存在
        if (null == file) {
            return false;
        }
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否是excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            return false;
        }
        return true;
    }


}
