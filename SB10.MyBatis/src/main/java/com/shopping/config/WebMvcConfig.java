package com.shopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// WebMvcConfigurer : 스프링 MVC에서 웹 애플리케이션 관련 설정을 하고자 할때 사용합니다.
// 용도 : 리소스 핸들링, CORS 설정 등등
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}") // application.properties 파일의 uploadPath 항목을 읽어서 변수에 저장합니다.
    String uploadPath ; // file:///c:/shop/

    // 3000번 포트에서 들어오는 모든 요청에 대하여 CORS 설정을 적용하겠습니다.
    @Override // Cors 교차 출처 리소스 공유 정책
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("OPTIONS", "GET", "PUT", "POST", "DELETE");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 인터넷 주소 요청시 `/images` 또는 `/image`라는 요청이 들어 오면 uploadPath라는 경로에서 해당 데이터를 찾아서 읽어 들이겠습니다.
        registry
                .addResourceHandler("/images/**", "/image/**")
                .addResourceLocations(uploadPath) ;
    }
}
