package com.bbc.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;

/**
 * (DailyProduction)表实体类
 *
 * @author makejava
 * @since 2024-02-05 16:46:40
 */
@SuppressWarnings("serial")
@Data
public class DailyProduction extends Model<DailyProduction> {
    @ExcelProperty(index = 0,value = "日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date dailyDate;
    @ExcelProperty(index = 1,value = "井号")
    private String wellName;
    @ExcelProperty(index = 2,value = "生产时间(h)")
    private Float productionTime;
    @ExcelProperty(index = 3,value = "油压(MPa)")
    private Float oilPressure;
    @ExcelProperty(index = 4,value = "套压(MPa)")
    private Float casingPressure;
    @ExcelProperty(index = 5,value = "日产气量(万方)")
    private Float gas;

    @ExcelProperty(index = 6,value = "日产水量(万方)")
    private Float water;
    @ExcelProperty(index = 7,value = "进站压力(MPa)")
    private Float inPressure;
    @ExcelProperty(index = 8,value = "备注")
    private String remark;

    private String id;

    private String wellId;


    public Date getDailyDate() {
        return dailyDate;
    }

    public void setDailyDate(Date dailyDate) {
        this.dailyDate = dailyDate;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public Float getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Float productionTime) {
        this.productionTime = productionTime;
    }

    public Float getOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(Float oilPressure) {
        this.oilPressure = oilPressure;
    }

    public Float getCasingPressure() {
        return casingPressure;
    }

    public void setCasingPressure(Float casingPressure) {
        this.casingPressure = casingPressure;
    }

    public Float getGas() {
        return gas;
    }

    public void setGas(Float gas) {
        this.gas = gas;
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public Float getInPressure() {
        return inPressure;
    }

    public void setInPressure(Float inPressure) {
        this.inPressure = inPressure;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWellId() {
        return wellId;
    }

    public void setWellId(String wellId) {
        this.wellId = wellId;
    }

}

