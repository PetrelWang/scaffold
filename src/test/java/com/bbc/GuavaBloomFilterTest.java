package com.bbc;

import com.bbc.dto.Person;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Title: BloomFilterTest
 * @Author WangHaoWei
 * @Package com.bbc
 * @Date 2024/3/15 16:27
 * @description: google旗下的GUAVA布隆过滤器的演示环境
 * 只能单机使用，分布式请用 Redis的布隆过滤器
 */

public class GuavaBloomFilterTest {
    /**
     * 过滤单个数值
     */
    @Test
    public void getOneNum(){
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 5000000);
        //准备500万条假数据
        for (int i = 0; i < 5000000; i++) {
            bloomFilter.put(i);
        }
        long start = System.nanoTime();
        if (bloomFilter.mightContain(500000)) {
            System.out.println("成功过滤到500000");
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间"+(end - start)/1000L+"微秒");
    }

    /**
     * 过滤单个对象
     */
    @Test
    public void getOneObject(){
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 5000000);
        //准备500万条假数据
        for (int i = 0; i < 2000000; i++) {
            Person p = new Person();
            p.setAge(i);
            p.setName("name"+i);
            bloomFilter.put(p.toString());
        }
        long start = System.nanoTime();
        Person p = new Person("name156001",156001);
        if (bloomFilter.mightContain(p.toString())) {
            System.out.println("成功过滤到156001");
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间"+(end - start)/1000L+"微秒");
    }

    /**
     * 误判率是多少
     */
    @Test
    public void existErrorRate(){
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),5000000,0.01);
        List<String> list = new ArrayList<>(5000000);
        for (int i = 0; i < 5000000; i++) {
            String uuid = UUID.randomUUID().toString();
            bloomFilter.put(uuid);
            list.add(uuid);
        }
        int mightContainNumber1= 0;
        NumberFormat percentFormat =NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);

        for (int i=0;i < 500;i++){
            String key = list.get(i);
            if (bloomFilter.mightContain(key)){
                mightContainNumber1++;
            }
        }
        System.out.println("【key真实存在的情况】布隆过滤器认为存在的key值数：" + mightContainNumber1);
        System.out.println("================================================================================");
        int mightContainNumber2 = 0;
        for (int i=0;i < 5000000;i++){
            String key = UUID.randomUUID().toString();
            if (bloomFilter.mightContain(key)){
                mightContainNumber2++;
            }
        }

        System.out.println("【key不存在的情况】布隆过滤器认为存在的key值数：" + mightContainNumber2);
        System.out.println("【key不存在的情况】布隆过滤器的误判率为：" + percentFormat.format((float)mightContainNumber2 / 5000000));
    }

    /**
     * 指定误判率不超过1%
     */
    @Test
    public void bool(){
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 500, 0.01);
        // 判断指定元素是否存在
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
        // 将元素添加进布隆过滤器
        filter.put(1);
        filter.put(2);
        // 判断指定元素是否存在
        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(2));
    }

}
