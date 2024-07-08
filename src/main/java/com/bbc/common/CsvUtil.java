package com.bbc.common;

/**
 * @Title: CsvUtil
 * @Author WangHaoWei
 * @Package com.cnpc.paopai.util
 * @Date 2023/11/6 15:14
 * @description: csv工具包
 */

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : HMF
 * @ClassName CsvUtil
 * @description 读取和写入csv的工具类
 * @date: 2022/2/25 21:37
 **/


public class CsvUtil {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtil.class);

    /**
     * 解析csv文件并转成bean
     * @param file csv文件
     * @param clazz 类
     * @param <T> 泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvData(InputStream file, Class<T> clazz) {
        BufferedReader fr = null;
        try {
            fr = new BufferedReader(new InputStreamReader(file));
        } catch (Exception e) {
            LOGGER.error("读取csv文件失败");
        }

        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(fr)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }
    public  static <T> void writeToCsv(String csvFilePath,List<T> datalist) {
        File file = new File(csvFilePath);
        boolean fileExists = file.exists();

        try (FileWriter fileWriter = new FileWriter(file, true)) {
            if (!fileExists) {
                // 如果文件不存在，则写入CSV文件的表头
                String header = "well_name,date_time,type,value"; // 自定义表头
                fileWriter.append(header);
                fileWriter.append("\n");
            }

            for (T object : datalist) {
                // 将每个对象的数据写入CSV文件的一行
                String csvRow = convertObjectToCSVRow(object); // 将对象转换为CSV行
                fileWriter.append(csvRow);
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
        }
    }

    public static String convertObjectToCSVRow(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        String csvRow = Arrays.stream(fields)
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        if(field.getName().equals("dateTime")){
                            Date date = (Date)field.get(object);
                            return field.get(object)!=null ? DateTimeUtil.dateToString(date,DateTimeUtil.FORMAT_DATE_TIME).toString():"";
                        }
                        Object value = field.get(object);
                        return value != null ? value.toString() : "";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .collect(Collectors.joining(","));

        return csvRow;
    }
}
