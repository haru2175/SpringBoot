package com.shopping.mycartest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CarMain04 {
    // ApplicationContext : 애플리케이션의 객체 생성과 주입 담당자 인터페이스
    // AnnotationConfigApplicationContext : 어노테이션 기반의 설정 정보를 가져 오고자 할때 사용하는 구현체 클래스

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(CarConfig.class);

        System.out.println("@Bean('객체이름')을 호출하는 방식");
        Car03 avante01 = context.getBean("avante01", Car03.class);
        System.out.println(avante01);

        System.out.println("@Bean('객체이름')을 호출하는 방식");
        Car03 sonata01 = context.getBean("sonata01", Car03.class);
        System.out.println(sonata01);

        System.out.println("메소드 이름을 참조하는 방식");
        Car03 makeAvante02 = context.getBean("makeAvante02", Car03.class);
        System.out.println(makeAvante02);

        System.out.println("person01 객체 호출해보기");
        Person03 person01 = context.getBean("person01", Person03.class);
        System.out.println(person01);

        System.out.println("컬렉션 정보");
        List<Car03> carList = (List<Car03>)context.getBean("carlist");
        for(Car03 car : carList){
            System.out.println(car);
        }
    }
}
