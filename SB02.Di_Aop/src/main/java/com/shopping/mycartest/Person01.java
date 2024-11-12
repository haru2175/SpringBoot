package com.shopping.mycartest;

import lombok.ToString;

@ToString
public class Person01 {
    private String name ;
    private int age;
    private String gender ;

    // Dependency : 현재 Person01 객체가 Avante01 객체를 참조(의존)하고 있습니다.
    private Avante01 avante ;

    // Injection(주입) : 무의미한 정보에 의미가 있는 정보를 넣어 주는 동작
    // 생성자(Constructor) 인젝션
    public Person01(String name, int age, String gender) {
        this.gender = gender;
        this.age = age;
        this.name = name;

        this.avante = new Avante01() ;

        // Setter Injection
        this.avante.setName("아반떼 흰차");
        this.avante.setPrice(100);
        this.avante.setComment("차량이 이쁩니다.");
    }
}
/* 강한 결합력(tight coupling)
* 특정 클래스 Person01 내에서 다른 클래스 Avante01에 대한 객체를 직접 생성하고, 값도 직접 대입하는 방식 */