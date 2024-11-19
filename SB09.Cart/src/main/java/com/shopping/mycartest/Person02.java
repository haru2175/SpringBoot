package com.shopping.mycartest;

import lombok.ToString;

@ToString
public class Person02 {
    private String name ;
    private int age;
    private String gender ;
    private Avante02 avante ;

    public Person02(String name, int age, String gender, Avante02 avante) {
        this.gender = gender;
        this.age = age;
        this.name = name;
        this.avante = avante ;
    }
}