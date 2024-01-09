package com.bbc.dto;

import cn.hutool.core.comparator.CompareUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Title: Person
 * @Author WangHaoWei
 * @Package com.bbc.dto
 * @Date 2023/11/30 10:53
 * @description:
 */
@Data
public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Person other) {
        if(StringUtils.isEmpty(other.name)){
           return CompareUtil.compare(other.name,this.name,false);
        }
        return this.name.compareTo(other.name);
    }

    public static void main(String[] args) {
        Contact build = new Contact.Builder().age(13).build();
        System.out.println(build.toString());
    }
}
