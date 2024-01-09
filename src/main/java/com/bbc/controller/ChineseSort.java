package com.bbc.controller;

/**
 * @Title: ChineseSort
 * @Author WangHaoWei
 * @Package com.bbc.controller
 * @Date 2023/11/30 10:43
 * @description:
 */
import com.bbc.dto.Person;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ChineseSort {
    public static void main(String[] args) {
        List<String> layerList =new ArrayList<>();
        layerList.add("Ⅰ-1");
        layerList.add("○-1");
        layerList.add("○-2");
        layerList.add("Ⅱ-2");
        layerList.add("Ⅲ-1");
        List<String> resultList=  sortLayerList(layerList);
        System.out.println(resultList.toString());
    }

    private static List<String> sortLayerList(List<String> layerList) {
        List<String> sortedCollection = new ArrayList<>();
        List<String> oList =new ArrayList<>();
        List<String> otherList= new ArrayList<>();
        if(CollectionUtils.isNotEmpty(layerList)){
            for (String s : layerList) {
                if(s.contains("○")){
                    oList.add(s);
                }else{
                   otherList.add(s);
                }
            }
            List<String> collect = oList.stream().sorted(Comparator.comparing(String::toString)).collect(Collectors.toList());
            sortedCollection.addAll(collect);
            List<String> collect1 = otherList.stream().sorted(Comparator.comparing(String::toString)).collect(Collectors.toList());
            sortedCollection.addAll(collect1);
        }
        return sortedCollection;
    }

    public static  List<Person> sortCollection(List<Person> persons){
        List<Person> s1Collect = new ArrayList<>();
        List<Person> s2Collect = new ArrayList<>();
        List<Person> s3Collect = new ArrayList<>();
        List<Person> nullCollect = new ArrayList<>();
        List<Person> otherCollect = new ArrayList<>();
        for (Person person : persons) {
            if(person.getName().equals("涩北一号")){
                s1Collect.add(person);
            }else if(person.getName().equals("涩北二号")){
                s2Collect.add(person);
            }else if(person.getName().equals("涩北三号")){
                s3Collect.add(person);
            }else if(StringUtils.isEmpty(person.getName())){
                nullCollect.add(person);
            }else {
                otherCollect.add(person);
            }
        }
        List<Person> resultList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(s1Collect)){
            List<Person> collect = s1Collect.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
            resultList.addAll(collect);
        }
        if(CollectionUtils.isNotEmpty(s2Collect)){
            List<Person> collect = s2Collect.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
            resultList.addAll(collect);
        }
        if(CollectionUtils.isNotEmpty(s3Collect)){
            List<Person> collect = s3Collect.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
            resultList.addAll(collect);
        }
        if(CollectionUtils.isNotEmpty(otherCollect)){
            Map<String, List<Person>> collect1 = otherCollect.stream().collect(Collectors.groupingBy(x -> x.getName()));
            for (Map.Entry<String, List<Person>> stringListEntry : collect1.entrySet()) {
                List<Person> value = stringListEntry.getValue();
                if(CollectionUtils.isNotEmpty(value)){
                    List<Person> collect = value.stream().sorted(Comparator.comparing(item -> item.getAge())).collect(Collectors.toList());
                    resultList.addAll(collect);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(nullCollect)){
            List<Person> collect = nullCollect.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
            resultList.addAll(collect);
        }
        return resultList;
    }
}


