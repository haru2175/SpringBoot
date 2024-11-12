package com.shopping.mycartest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CarConfig {

    @Bean("avante01")  //이름 지정하지 않으면 메서드 이름으로 bean 등록됨
    public Car03 makeAvante01() {
        Car03 myCar = new Avante03("ㅇㅇ", 111, "dd");
        return myCar;
    }

    @Bean("sonata01")
    public Car03 makeSonata01() {
        Car03 myCar = new Sonata03("dd", 111, "dd");
        return myCar;
    }

    @Bean
    public Car03 makeAvante02(){
        Car03 mycar = new Avante03("아반떼 승용차", 222,"서민의 차");
        return mycar;
    }

    @Bean("carList")
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
