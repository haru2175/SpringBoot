package com.shopping.mycartest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CarMain04 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CarConfig.class);

        System.out.println("@Bean('객체이름')을 호출하는 방식");
        Car03 avante01 = context.getBean("avante01", Car03.class);
        System.out.println("avante01 = " + avante01);
        Car03 sonata01 = context.getBean("sonata01", Car03.class);
        System.out.println("sonata01 = " + sonata01);

        System.out.println("메서드 이름을 참조하는 방식");
        Car03 makeAvante02 = context.getBean("makeAvante02", Car03.class);
        System.out.println("makeAvante02 = " + makeAvante02);

        System.out.println("@Bean(Person01) 객체 호출");
        Person03 person01 = context.getBean("person01", Person03.class);
        System.out.println("person01 = " + person01);

        List<Car03> carList = (List<Car03>) context.getBean("carList");
        for (Car03 car : carList) {
            System.out.println("car = " + car);
        }



//        Car03 car = context.getBean(Car03.class);


    }
}
