package com.bbc.dto;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Title: Contact
 * @Author WangHaoWei
 * @Package com.bbc.dto
 * @Date 2023/12/6 17:08
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String name;
    private byte sex;
    private int age;
    private long phone;

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", phone=" + phone +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
    public static class Builder{
        private String name;
        private byte sex;
        private int age;
        private long phone;
        public Builder(){

        }
        public Builder(String name){
            this.name = name;
        }
        public Builder sex(byte val){
            this.sex = val;
            return this;
        }
        public Builder age(int val){
            this.age = val;
            return this;
        }
        public Builder phone(long val){
            this.phone = val;
            return this;
        }
        public Contact build(){
            return new Contact(this);
        }
    }
    private Contact(Builder b){
        this.name = b.name;
        this.age = b.age;
        this.sex = b.sex;
        this.phone = b.phone;
    }
}
