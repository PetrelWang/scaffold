package com.bbc.controller;

import com.bbc.common.DateTimeUtil;
import com.bbc.dto.ResponseResult;
import com.bbc.entity.WellPlunger;
import com.bbc.mapper.WellPlungerMapper;
import com.bbc.service.WellPlungerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Title: TestController
 * @Author WangHaoWei
 * @Package com.bbc.controller
 * @Date 2024/2/22 19:30
 * @description: 测试
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private WellPlungerService wellPlungerService;
    @Autowired
    private WellPlungerMapper wellPlungerMapper;

    @GetMapping("save")
    public void save() {
        List<WellPlunger> list = new ArrayList<>();
        int count = 0;
        int num =1;
        String startDate = "2032-07-31 10:06:40";
        Date date = DateTimeUtil.stringToDate(startDate);
        while (true) {
            //一秒钟保存一条数据
            WellPlunger wellPlunger = new WellPlunger();
            wellPlunger.setWellName("test");
            wellPlunger.setType("test");
            wellPlunger.setDateTime(date);
            date = DateTimeUtil.plusSecond(date,10);
            wellPlunger.setValue(""+num++);
            list.add(wellPlunger);
            if (CollectionUtils.isNotEmpty(list) && list.size() == 1000) {
                wellPlungerService.saveBatch(list);
                log.info("保存了:{}条数据",count);
                count += list.size();
                list.clear();
            }
            if (count > 200000000) {
                break;
            }
        }
    }

    @GetMapping("get")
    public ResponseResult doGet(String wellName, String startDate, String endDate,String filePath) {
        long startTime = System.currentTimeMillis();
        List<WellPlunger> list = wellPlungerMapper.getDataList(wellName, startDate, endDate);
        long endTime = System.currentTimeMillis();
        log.info("一共花费：{}秒时间查询了:{}条数据", (endTime - startTime) / 1000, list.size());
        return ResponseResult.success(list);
    }
    private static List<WellPlunger> dataList= new ArrayList<>();
    private static int num= 0;
    {
        for (int i = 0; i < 10000; i++) {
            WellPlunger wellPlunger = new WellPlunger();
            wellPlunger.setWellName("test");
            wellPlunger.setType("test");
            wellPlunger.setDateTime(new Date());
            wellPlunger.setValue(""+num++);
            dataList.add(wellPlunger);
        }
    }
    @GetMapping("getDataList")
    public ResponseResult getDataList(){
        return ResponseResult.success(dataList);
    }
}
