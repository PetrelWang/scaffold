package com.bbc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

import com.bbc.handle.JsonbTypeHandler;
import lombok.Data;

/**
 * (DataItem)表实体类
 *
 * @author wanghaowei
 * @since 2024-04-09 10:41:25
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "data_item",autoResultMap = true)
public class DataItem extends Model<DataItem> {

    @TableId(type = IdType.INPUT)
    private String id;
//数据
    @TableField(value = "data_info",typeHandler = JsonbTypeHandler.class)
    private Object dataInfo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

