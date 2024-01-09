package com.bbc;

import com.bbc.person.PeopleFactory;
import com.bbc.person.PersonService;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Title: Test
 * @Author WangHaoWei
 * @Package com.bbc
 * @Date 2023/9/22 10:49
 * @description: 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MinioTest {
@Autowired
private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test()  {
//        ActionServiceFactory.getInstance().action("action2");
        PersonService student = PeopleFactory.personByCode("worker");

        student.eat();
    }
    @Test
    public void valueTest(){
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }

}
