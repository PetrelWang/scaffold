package com.bbc.person.impl;

import com.bbc.person.PeopleFactory;
import com.bbc.person.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @Title: StudentImpl
 * @Author WangHaoWei
 * @Package com.bbc.person.impl
 * @Date 2023/12/14 18:29
 * @description:
 */
@Service
public class StudentImpl implements PersonService, InitializingBean {
    private StudentImpl(){}
    @Override
    public void eat() {
        System.out.println("学生在吃饭");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PeopleFactory.register("student",this);
    }
}
