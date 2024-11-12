package com.shopping.mycartest;

import lombok.ToString;

@ToString
public class Person03 {
    private String name ;
    private int age;
    private String gender ;
    private Car03 car ;

    public Person03(String name, int age, String gender, Car03 car) {
        this.gender = gender;
        this.age = age;
        this.name = name;
        this.car = car ;
    }
}