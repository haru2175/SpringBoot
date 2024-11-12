package com.shopping.mycartest;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class CarConfig {
    @Bean("avante01")
    public Car03 makeAvante01(){
        Car03 mycar = new Avante03("아반떼 빨간차", 111, "외관이 이뻐요");
        return mycar ;
    }
    @Bean("sonata01")
    public Car03 makeSonata01(){
        Car03 mycar = new Sonata03("소나타 센슈어스", 333,"HYUNDAI 차");
        return mycar;
    }
    @Bean // 이름을 구체적으로 명시하지 않으면, 메소드 이름이 곧 객체 이름이 됩니다.
    public Car03 makeAvante02(){
        Car03 mycar = new Avante03("아반떼 승용차", 222,"서민의 차");
        return mycar;
    }
    @Bean("carlist")
    public List<Car03> carList(){
        List<Car03> lists = new ArrayList<>();
        lists.add(this.makeAvante01());
        lists.add(this.makeSonata01());
        lists.add(this.makeAvante02());
        return lists;
    }

    @Bean("person01")
    public Person03 person(){
        Person03 person = new Person03("이정석", 30, "성별", this.makeAvante01()) ;
        return person ;
    }
}
