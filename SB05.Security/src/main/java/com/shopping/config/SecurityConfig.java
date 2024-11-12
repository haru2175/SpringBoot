package com.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스는 설정용 파일로 사용할 겁니다.
@EnableWebSecurity // Spring Security를 활성화 시켜주는 어노테이션입니다.
public class SecurityConfig { // 시큐리티를 위한 설정 파일

    @Bean // 인증, 인가, 로그인 등의 처리를 할 예정입니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.build();
    }

    @Bean(value = "passwordEncoder") // 비밀 번호 암호화를 위한 Bean입니다.
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
