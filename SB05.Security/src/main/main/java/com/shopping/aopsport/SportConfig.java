package com.shopping.aopsport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration  //스프링 설정 클래스
public class SportConfig {
    @Bean("soccer01")
    public Football method01() {
        return new Football();
    }

    @Bean("baseball")
    public Baseball method02() {
        return new Baseball();
    }
}
