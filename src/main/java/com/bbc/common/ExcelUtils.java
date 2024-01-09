package com.bbc.common;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Title: ExcelUtils
 * @Author WangHaoWei
 * @Package com.bbc.common
 * @Date 2023/11/25 10:47
 * @description:
 */
public class ExcelUtils {
    /**
     * 设置浏览器下载excel响应体信息
     *
     * @param response
     * @param fileName
     */
    public static void setExcelResponse(HttpServletResponse response, String fileName) {
        try {
            fileName = URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\T14\\Desktop";
        File file = new File(filePath+"\\"+"test");
        file.mkdirs();
        boolean directory = file.isDirectory();
        System.out.println("是否是一个文件夹"+directory);
        System.out.println("创建文件夹成功");
    }
}
