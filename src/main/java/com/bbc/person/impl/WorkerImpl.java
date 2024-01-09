package com.bbc.person.impl;

import com.bbc.person.PeopleFactory;
import com.bbc.person.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @Title: WorkerImpl
 * @Author WangHaoWei
 * @Package com.bbc.person.impl
 * @Date 2023/12/14 18:23
 * @description:
 */
@Service
public class WorkerImpl implements PersonService, InitializingBean {
    private WorkerImpl(){}
    @Override
    public void eat() {
        System.out.println("工人正在吃饭");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    //将对象注册到工厂上

        PeopleFactory.register("worker",this);
    }
}
