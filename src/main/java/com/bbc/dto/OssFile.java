package com.bbc.dto;

/**
 * @Title: OssFile
 * @Author WangHaoWei
 * @Package com.bbc.dto
 * @Date 2024/1/8 16:07
 * @description:
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * OssFile
 *
 * @author meng
 */
@Data
@ApiModel("文件返回对象")
public class OssFile {
    /**
     * 文件地址
     */
    @ApiModelProperty(value = "文件地址")
    private String filePath;
    /**
     * 域名地址
     */
    @ApiModelProperty(value = "域名地址")
    private String domain;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String name;
    /**
     * 原始文件名
     */
    @ApiModelProperty(value = "原始文件名")
    private String originalName;
    /**
     * 文件hash值
     */
    @ApiModelProperty(value = "文件hash值" )
    public String hash;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private long size;
    /**
     * 文件上传时间
     */
    private Date putTime;
    /**
     * 文件contentType
     */
    private String contentType;
}