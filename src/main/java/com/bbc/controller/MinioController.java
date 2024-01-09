package com.bbc.controller;

import com.bbc.constant.HttpStatusEnum;
import com.bbc.dto.OssFile;
import com.bbc.dto.ResponseResult;
import com.bbc.service.impl.MinioTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: MinioController
 * @Author WangHaoWei
 * @Package com.bbc.controller
 * @Date 2024/1/8 16:20
 * @description:
 */
@RestController
@RequestMapping("minio")
@Api(tags = "minio")
public class MinioController {
    @Autowired
    private MinioTemplate minioTemplate;

    @PostMapping("upload")
    @ApiOperation(value = "上传文件")
    public ResponseResult upload(MultipartFile file) throws IOException {
        String folderName = "testFolder";
        String fileName = "testFile";
        String suffix = "txt";
        OssFile ossFile = minioTemplate.upLoadFile(folderName, fileName, suffix, file.getInputStream());
        return ResponseResult.success(ossFile);
    }
    @ApiOperation(value = "下载文件")
    @GetMapping("download")
    public ResponseResult downloadFile(HttpServletResponse response,String fileName,String filePath){
        minioTemplate.downloadFile(response,fileName,filePath);
        return ResponseResult.success(HttpStatusEnum.SUCCESS);
    }
    @GetMapping("getOssInfo")
    @ApiOperation(value = "获取文件信息")
    public ResponseResult getOssInfo(String fileName){
        OssFile ossInfo = minioTemplate.getOssInfo(fileName);
        return ResponseResult.success(ossInfo);
    }
}
