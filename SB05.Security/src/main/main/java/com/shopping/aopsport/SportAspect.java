package com.shopping.aopsport;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component  //해당 클래스는 스프링의 구성 요소 중 1개로 인식
public class SportAspect {
//    @Before("execution(public void com.shopping.aopsprot.Football.firstHalf()) || execution(public void com.shopping.aopsprot.Baseball.frontInnings())")
//    public void action01() {
//        System.out.println("1. 애국가 제창");
//    }
//
//    // 시축 또는 시구
//    @Before("execution(public void com.shopping.aopsport.Football.firstHalf()) || execution(public void com.shopping.aopsport.Baseball.frontInnings())")
//    public void doFirstPitchOrKick() {
//        System.out.println("2. 시축/시구");
//    }
//
//    // 경기 중간에 이벤트가 있습니다.
//    @AfterReturning("execution(* com.shopping.aopsport.Football.firstHalf()) || execution(public void com.shopping.aopsport.Baseball.frontInnings())")
//    public void duringHalfTime() {
//        System.out.println("3. 이벤트 행사");
//    }
//
//    @After("execution(public void com.shopping.aopsport.Football.secondHalf()) || execution(public void com.shopping.aopsport.Baseball.rearInnings())")
//    @Order(2)
//    public void exitAudience() {
//        System.out.println("4. 관객 퇴장");
//    }
//
//    @After("execution(public void com.shopping.aopsport.Football.secondHalf()) || execution(public void com.shopping.aopsport.Baseball.rearInnings())")
//    @Order(1)
//    public void cleanStadium() {
//        System.out.println("5. 청소하기");
//    }

}
