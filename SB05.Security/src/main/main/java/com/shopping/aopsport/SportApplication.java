package com.shopping.aopsport;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class SportApplication {
    public static void main(String[] args) {
        //이 메서드를 이용하여 해당 클래스를 실행
        //ApplicationContext 인스턴스 생성 + Bean 생성
        SpringApplication.run(SpringApplication.class, args);
    }

    // CommandLineRunner는 애플리케이션 시작 후 특정 로직을 실행하기 위한 인터페이스입니다.
    // 여기서 'run' 메서드는 애플리케이션이 시작된 후 실행됩니다.
    @Bean
    public CommandLineRunner run(ApplicationContext context) {
        return args -> {
            Football soccer01 = (Football) context.getBean("soccer01");
            soccer01.firstHalf();
            soccer01.secondHalf();
            System.out.println();
            Baseball baseball01 = (Baseball) context.getBean("baseball01");
            baseball01.frontInnings();
            baseball01.rearInnings();
        };
    }
}
