package com.shopping.mycartest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person01 {

    private String name;
    private int age;
    private String gender;

    //현재 Person01 객체는 Avante01 객체 참조(의존) 중
    private Avante01 avante;

    //Injection(주입) : 무의미한 정보에 의미가 있는 정보를 넣어주는 동작
    public Person01(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;

        this.avante = new Avante01();
        this.avante.setName("아반떼(WH)");
        this.avante.setPrice(100);
        this.avante.setComment("???");
    }

}

//강한 결합력(tight coupling)
//특정 클래스 Person01 내에서 다른 클래스 Avante01 에 대한 객체를 직접 생성하고 값도 직접 대입하는 방식

