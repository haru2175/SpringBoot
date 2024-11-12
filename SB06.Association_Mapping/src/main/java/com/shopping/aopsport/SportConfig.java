package com.shopping.aopsport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration // 이 파일은 스프링에서 설정 파일로 사용해 주세요.
public class SportConfig {
    @Bean("soccer01")
    public FootBall method01(){
        return new FootBall();
    }

    @Bean("baseball01")
    public BaseBall method02(){
        return new BaseBall();
    }
}
