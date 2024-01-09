package com.bbc.common;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ReadExcelData {

    public static List<List<String>> returnTableGradientExcelData(String filePath) throws IOException, IllegalAccessException, ParseException {
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        //文件路径
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        //获得最后一排数
        int lastRowNum = sheet.getLastRowNum();

        //获取总数
        int peopleNum = lastRowNum;
        List<List<String>> result = new ArrayList<>();
        int k = 0;
        for (int i = 0; i <= peopleNum; i++) {
                Row row = sheet.getRow(k);
                List<String> part = new ArrayList<>();
                for (int j = 0; j <= row.getPhysicalNumberOfCells()-1; j++) {
                    String string = getString(row.getCell(j));
                    if(k==0&&j==0){
                        part.add(string);
                        break;
                    }
                    if(k>=3&&j==0){
                        BigDecimal v = new BigDecimal(string).setScale(0,BigDecimal.ROUND_HALF_UP);;
                        part.add(v.toString());
                        continue;
                    }
                    part.add(string);
                }
                result.add(part);

            //一行读取一次
            k += 1;
        }
        result.get(2).set(0,"序号");
        return result;
    }

    public static String getString(Cell cell) {
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return cell.getNumericCellValue() + "";
        } else {
            return cell.getStringCellValue();
        }
    }

    public static List<List<String>> returnTableTestResultData(String tableTestResultFilePath) throws FileNotFoundException {
        File file = new File(tableTestResultFilePath);
        FileInputStream inputStream = new FileInputStream(file);
        //文件路径
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        //获得最后一排数
        int lastRowNum = sheet.getLastRowNum();

        //获取总数
        List<List<String>> result = new ArrayList<>();
        int k = 0;
        int peopleNum = lastRowNum-k;
        for (int i = 0; i <= peopleNum; i++) {
                Row row = sheet.getRow(k);
                List<String> part = new ArrayList<>();
                for (int j = 0; j <= row.getPhysicalNumberOfCells()-1; j++) {
                    String string = getString(row.getCell(j));
                        part.add(string);
                }
                result.add(part);
            //一行读取一次
            k += 1;
        }
        result.get(1).set(0,"测试时间");
        result.get(1).set(1,"油嘴");
        result.get(1).set(2,"日产油");
        result.get(1).set(3,"日产水");
        result.get(1).set(4,"日产气");
        result.get(1).set(5,"含水");
        result.get(2).set(0,"测试时间");
        return result;
    }

    /**
     *
     * @param filePath 文件的绝对路径
     * @param firstRowIndex 从第几行开始读的索引
     * @param dataRowIndex 从真实数据第几行开始读的索引
     * @return
     * @throws FileNotFoundException
     */
    public static List<List<String>> returnTableDataExcelData(String filePath,int firstRowIndex,int dataRowIndex) throws FileNotFoundException {
        Boolean flag = false;
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        //文件路径
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        //获得最后一排数
        int lastRowNum = sheet.getLastRowNum();

        //获取总数
        List<List<String>> result = new ArrayList<>();
        int k = firstRowIndex;
        int peopleNum = lastRowNum-k;
        for (int i = k; i <= peopleNum; i++) {
                Row row = sheet.getRow(k);
                if(row ==null){
                    break;
                }
                List<String> part = new ArrayList<>();
                for (int j = 0; j <= row.getPhysicalNumberOfCells()-1; j++) {
                    String string = getString(row.getCell(j));
                    if(string.equals("序号")){
                        flag =true;
                    }
                    if(k>dataRowIndex &&StringUtils.isEmpty(string)){
                        part.add(string);
                        continue;
                    }
                    if(k<dataRowIndex && StringUtils.isEmpty(string)){
                        continue;
                    }
                   if(k>=dataRowIndex&&flag&&j==0){
                        BigDecimal v = new BigDecimal(string).setScale(0,BigDecimal.ROUND_HALF_UP);;
                        part.add(v.toString());
                        continue;
                    }
                    part.add(string);
                }
                result.add(part);

            //一行读取一次
            k += 1;
        }
        return result;
    }
}

