package com.shopping.mycartest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person03 {

    private String name;
    private int age;
    private String gender;

    private Car03 car;

    //Injection(주입) : 무의미한 정보에 의미가 있는 정보를 넣어주는 동작
    public Person03(String name, int age, String gender, Car03 car) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.car = car;
    }
}
