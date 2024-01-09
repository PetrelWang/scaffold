package com.bbc.common;

/**
 * @Title: DirectoryStructure
 * @Author WangHaoWei
 * @Package com.bbc.common
 * @Date 2023/12/4 10:26
 * @description:
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DirectoryStructure {
    public static void main(String[] args) throws Exception {
        // 指定文件夹路径
        String folderPath = "C:\\Users\\T14\\Desktop\\test";
        // 创建File对象
        File folder = new File(folderPath);
        if(folder.exists()){
            System.out.println("存在");
        }else{
            System.out.println("不存在");
            folder.mkdirs();
            System.out.println("创建文件夹成功");
        }
        // 获取文件夹中的所有文件和子文件夹
        //printDirectoryStructure(folder);

        // 源文件或源文件夹路径
        String sourcePath = "C:\\Users\\T14\\Desktop\\test";
        // 目标文件或目标文件夹路径
        String destinationPath = "C:\\Users\\T14\\Desktop\\test1";
        copyDir(sourcePath,destinationPath);

    }

    // 递归遍历文件夹的方法
    private static void printDirectoryStructure(File folder) {
        // 获取文件夹中的所有文件和子文件夹
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            // 遍历文件列表
            for (File file : listOfFiles) {
                // 打印文件或子文件夹的名称
                System.out.println(file.getName());
                // 如果当前文件是一个文件夹，则递归遍历其内容
                if (file.isDirectory()) {
                    printDirectoryStructure(file);
                }
            }
        } else {
            System.out.println("文件夹为空或不存在！");
        }
    }
    //copy directory and files
    public static void copyDir(String oldPath,String newPath) throws Exception{
        File src = new File(oldPath);
        File tar = new File(newPath);
        File[] fs = src.listFiles();

        //create target directory
        if(!tar.exists()){
            tar.mkdirs();
        }

        //get current dir's files and dirs
        for(File f:fs){
            if(f.isFile()){
                //copy file
                copyFile(f,new File(newPath+"//"+f.getName()));
            }else if(f.isDirectory()){
                //recursion
                copyDir(f.getPath(),newPath+"//"+f.getName());
            }
        }
    }

    //copy files
    public static void copyFile(File src,File tar) throws Exception{
        //create inputStream buffer
        FileInputStream fis = new FileInputStream(src);
        BufferedInputStream bif = new BufferedInputStream(fis);

        //create outputStream buffer
        FileOutputStream fos = new FileOutputStream(tar);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        //buffer array
        byte[] bs = new byte[1024];
        int len;
        while((len=bif.read(bs))!=-1){
            bos.write(bs,0,len);
        }
        bos.flush();
        //close stream
        fis.close();
        fos.close();
        bif.close();
        bos.close();
    }


}