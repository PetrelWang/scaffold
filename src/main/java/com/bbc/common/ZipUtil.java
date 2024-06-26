package com.bbc.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip文件解压与压缩
 *
 * @author zhanglei
 * @date 创建时间：2016年11月24日 下午5:00:07
 */

public class
ZipUtil {


    /**
     * 压缩文件
     *
     * @param filePath 待压缩的文件路径
     * @return 压缩后的文件
     */

    public static File zip(String filePath) {
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            // 压缩文件名=源文件名.zip
            String zipName = source.getName() +
                    ".zip";
            target = new File(source.getParent(), zipName);
            if
            (target.exists()) {
                target.delete();
                // 删除旧的文件
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry("/", source, zos);
            } catch
            (IOException e) {
                throw new RuntimeException(e);
            } finally {
                ZipUtil.close(zos, fos);
            }
        }
        return target;
    }

    /**
     * 解压文件
     *
     * @param filePath 压缩文件路径
     * @return map key：文件名字，value:文件绝对路径
     */
    public static Map<String,String> unzip(String filePath) {
        Map<String,String> map = new HashMap<>();
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try { zis = new ZipInputStream(new FileInputStream(source), Charset.forName("GBK"));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null) {
                    if (!entry.isDirectory()) {
                        File target =new File(source.getParent(), entry.getName());
                        if (!target.getParentFile().exists()) {
                            // 创建文件父目录
                            target.getParentFile().mkdirs();
                        }
                        // 写入文件
                        bos = new BufferedOutputStream(new FileOutputStream(target));
                        int read = 0;
                        byte[] buffer = new byte[1024 * 10];
                        while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                            bos.write(buffer, 0, read);
                        }
                        bos.flush();
                        ZipUtil.close(bos);
                        map.put(target.getName(),target.getAbsolutePath());
                    }
                }
                zis.closeEntry();
            } catch
            (IOException e) {
                throw new RuntimeException(e);
            } finally {
                ZipUtil.close(zis, bos);
            }
        }
        return map;
    }


    /**
     * 扫描添加文件Entry
     *
     * @param base   基路径
     * @param source 源文件
     * @param zos    Zip文件输出流
     * @throws IOException
     */

    private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
        // 按目录分级，形如：/aaa/bbb.txt
        // String entry = base + source.getName();
        String entry = source.getName();
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                // 递归列出目录下的所有文件，添加文件Entry
                addEntry(entry + "/", file, zos);
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024 * 10];
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read = 0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                ZipUtil.close(bis, fis);
            }
        }
    }

    /**
     * 关闭流对象
     *
     * @param closeables 可关闭的流对象列表
     * @throws IOException
     */
    private static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch
                    (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
