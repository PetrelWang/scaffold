package com.bbc.person;

/**
 * @Title: PeopleFactory
 * @Author WangHaoWei
 * @Package com.bbc.person
 * @Date 2023/12/14 18:24
 * @description:
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂类
 */
public class PeopleFactory {
    public static final Map<String,PersonService> PERSON_MAP=new HashMap<>();
    public static PersonService personByCode(String code){
        return PERSON_MAP.get(code);
    }
    public static void register(String code ,PersonService personService){
       PERSON_MAP.put(code,personService);
    }
}
