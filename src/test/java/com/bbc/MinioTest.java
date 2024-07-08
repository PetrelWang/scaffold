package com.bbc;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bbc.common.CsvUtil;
import com.bbc.common.DateTimeUtil;
import com.bbc.common.JsonUtils;
import com.bbc.dto.Person;
import com.bbc.dto.ResponseResult;
import com.bbc.entity.DailyProduction;
import com.bbc.entity.DataItem;
import com.bbc.entity.Production;
import com.bbc.entity.WellPlunger;
import com.bbc.mapper.WellPlungerMapper;
import com.bbc.person.PeopleFactory;
import com.bbc.person.PersonService;
import com.bbc.service.DailyProductionService;
import com.bbc.service.DataItemService;
import com.bbc.service.MinioService;
import com.bbc.service.WellPlungerService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

/**
 * @Title: Test
 * @Author WangHaoWei
 * @Package com.bbc
 * @Date 2023/9/22 10:49
 * @description: 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MinioTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DataItemService dataItemService;
    @Test
    public void test() {
//        ActionServiceFactory.getInstance().action("action2");
        PersonService student = PeopleFactory.personByCode("worker");

        student.eat();
    }

    @Test
    public void valueTest() {
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    public void insertMongoTemplate() {
        Person person = new Person();
        person.setId(IdUtil.objectId());
        person.setAge(11);
        person.setName("john");
        mongoTemplate.insert(person);

    }

    @Test
    public void insertPgsql(){
        DataItem dataItem = new DataItem();
        dataItem.setId(IdUtil.simpleUUID());
        Map<String,String> map = new HashMap<>();
        for (int i = 0; i < 500000; i++) {
         map.put("测试"+i,""+i);
        }
        String json = JsonUtils.obj2String(map);
        dataItem.setDataInfo(json);
        dataItemService.save(dataItem);
    }

    @Test
    public void getPgSql(){
        LambdaQueryWrapper<DataItem> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DataItem::getId,"b21b944fa5cf4d3fb90d6ed681110cac");
        lambdaQueryWrapper.eq(DataItem::getId,"b21b944fa5cf4d3fb90d6ed681110cac");
        String id = "5f4537b8-62e3-4a3c-b5c0-1f74a0cfb745";
        DataItem one = dataItemService.getOne(lambdaQueryWrapper);
        System.out.println(one.toString());
    }
    @Test
    public void getMongoTemplate() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
        ObjectId objectId = new ObjectId("6613b028fbb3b82543bf2e43");
        Document query = new Document("_id",objectId);
        Document first = collection.find(query).first();
        if(first!=null){
            String json = first.toJson();
            System.out.println(json);
        }
    }
    @Autowired
    private DailyProductionService dailyProductionService;
    @Test
    public void testImport(){
        String filePath = "C:\\Users\\T14\\Desktop\\双31-15C5.xls";
        File file = new File(filePath);
        List<DailyProduction> dataList =new ArrayList<>();
          EasyExcel.read(file, DailyProduction.class, new AnalysisEventListener<DailyProduction>() {
            @Override
            public void invoke(DailyProduction o, AnalysisContext analysisContext) {
                 dataList.add(o);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet().headRowNumber(2).doReadSync();
          dataList.stream().forEach(x->{x.setId(IdUtil.randomUUID());
          x.setWellId("test");
          });
        dailyProductionService.saveBatch(dataList);
    }
    @Autowired
    private RestTemplate restTemplate;
    @Test
    public void testCal(){
        List<DailyProduction> dataList = dailyProductionService.list();
        System.out.println(dataList.toString());
        //将数据写入到csv文件中并调用算法
        String id = IdUtil.randomUUID();
        String path = getPath(id);
        File inputFile = null;
        int responseCode = 0;
        try {
            CsvUtil.writeToCsv(path,dataList);
            //调用http请求
            inputFile = new File(path);
            responseCode = senPost(inputFile, restTemplate, dataList.get(0).getWellName(), id);
        } catch (Exception e) {
            System.out.println("调用朱老师算法失败:{},uin:{}");
        }
        if(responseCode==200){
            System.out.println("文件id："+id);
        }

    }
    public  String getPath(String id){
        return System.getProperty("user.dir")+"/"+id+".csv";
    }
    public int senPost(File file, RestTemplate restTemplate, String wellName, String uid){
        String url ="http://localhost:8054/prediction/"+wellName+"/"+uid;
        // 构建请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 发送POST请求
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        // 处理响应
        return responseEntity.getStatusCodeValue();

    }
    @Autowired
    private WellPlungerMapper wellPlungerMapper;
//    @Autowired

    @Autowired
    private WellPlungerService wellPlungerService;
     @Test
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

}
