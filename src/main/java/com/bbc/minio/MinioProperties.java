package com.bbc.minio;

/**
 * @Title: MinioProperties
 * @Author WangHaoWei
 * @Package com.bbc.minio
 * @Date 2024/1/8 15:28
 * @description: minio配置类
 */
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "minio")  // 文件上传 配置前缀file.oss
public class MinioProperties implements Serializable {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
    private String readPath;
}