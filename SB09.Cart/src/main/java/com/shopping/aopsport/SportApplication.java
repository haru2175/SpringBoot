package com.shopping.aopsport;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
public class SportApplication {
    public static void main(String[] args) {
        // 이 메소드를 이용하여 SportApplication을 실행합니다.
        // 이때 ApplicationContext 인스턴스가 생성이 되고,
        // Bean들이 모두 생성이 됩니다.
        SpringApplication.run(SportApplication.class, args);
    }

    // CommandLineRunner는 애플리케이션 시작 후 특정 로직을 실행하기 위한 인터페이스입니다.
    // 여기서 'run' 메서드는 애플리케이션이 시작된 후 실행됩니다.
    @Bean
    public CommandLineRunner run(ApplicationContext context) {
        return args -> {
            FootBall soccer01 = (FootBall) context.getBean("soccer01");
            soccer01.firstHalf();
            soccer01.secondHalf();
            System.out.println();
            BaseBall baseball01 = (BaseBall) context.getBean("baseball01");
            baseball01.frontInnings();
            baseball01.rearInnings();
        };
    }
}
