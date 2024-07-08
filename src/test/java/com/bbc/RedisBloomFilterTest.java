package com.bbc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.text.NumberFormat;
import java.util.UUID;

/**
 * @Title: RedisBloomFilterTest
 * @Author WangHaoWei
 * @Package com.bbc
 * @Date 2024/3/15 16:43
 * @description: Redis的布隆过滤器的演示环境
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisBloomFilterTest {
    //定义操作Redisson的客户端实例
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 过滤单个对象
     */
    @Test
    public void test1(){
        String key="MyBloom";
        Integer total=500000;
        RBloomFilter<Integer> bloomFilter=redissonClient.getBloomFilter(key);
        //初始化布隆过滤器
        bloomFilter.tryInit(total,0.01);//精度越高，消耗空间越大
       /* //生成元素
        for(int i=1;i<=total;i++){
            bloomFilter.add(i);
        }*/
        long start = System.currentTimeMillis();
        if(bloomFilter.contains(50000)){
            System.out.println("找到你了，50000");
        }
        long end = System.currentTimeMillis();
        System.out.println("布隆过滤器消耗时间"+(end - start)+"毫秒");
    }

    /**
     * 误判率
     */
    @Test
    public void patchingConsume(){
        RBloomFilter<String> bloom = redissonClient.getBloomFilter("");
        // 初始化布隆过滤器；  大小:100000，误判率:0.01
        bloom.tryInit(100000L, 0.01);
        NumberFormat percentFormat =NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(2);
        int num = 0;
        // 新增10万条数据
        for(int i=0;i<100000;i++) {
            String uuid = UUID.randomUUID().toString();
            bloom.add(uuid);
        }
        for(int i=0;i<100000;i++) {
            String str = UUID.randomUUID().toString();
            boolean notExist = bloom.contains(str);
            if (notExist) {
                num++;
            }
        }
        System.out.println("误判率："+percentFormat.format(num/100000));

    }

}
