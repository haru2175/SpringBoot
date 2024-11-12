package com.shopping.mycartest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person02 {

    private String name;
    private int age;
    private String gender;

    private Avante02 avante;

    //Injection(주입) : 무의미한 정보에 의미가 있는 정보를 넣어주는 동작
    public Person02(String name, int age, String gender, Avante02 avante) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.avante = avante;
    }
}
