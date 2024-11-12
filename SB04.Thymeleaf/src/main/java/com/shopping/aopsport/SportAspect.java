package com.shopping.aopsport;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect // 해당 클래스는 Aspect로 사용됩니다.
@Component // 해당 클래스는 스프링의 구성 요소 중 1개입니다.
public class SportAspect { // AspectJ Expression 공부해야 합니다.
    @Before("execution(public void com.shopping.aopsport.FootBall.firstHalf()) || execution(public void com.shopping.aopsport.BaseBall.frontInnings())")
    public void asingAnthem(){
        System.out.println("1.애국가 제창");
    }

    // 시축 또는 시구
    @Before("execution(public void com.shopping.aopsport.FootBall.firstHalf()) || execution(public void com.shopping.aopsport.BaseBall.frontInnings())")
    public void doFirstPitchOrKick() {
        System.out.println("2.시축/시구");
    }


    // 경기 중간에 이벤트가 있습니다.
    @AfterReturning("execution(* com.shopping.aopsport.FootBall.firstHalf()) || execution(public void com.shopping.aopsport.BaseBall.frontInnings())")
    public void duringHalfTime() {
        System.out.println("3.이벤트 행사");
    }

    @After("execution(public void com.shopping.aopsport.FootBall.secondHalf()) || execution(public void com.shopping.aopsport.BaseBall.rearInnings())")
    @Order(2)
    public void exitAudience() {
        System.out.println("4.관객 퇴장");
    }

    @After("execution(public void com.shopping.aopsport.FootBall.secondHalf()) || execution(public void com.shopping.aopsport.BaseBall.rearInnings())")
    @Order(1)
    public void cleanStadium() {
        System.out.println("5.청소하기");
    }
}
