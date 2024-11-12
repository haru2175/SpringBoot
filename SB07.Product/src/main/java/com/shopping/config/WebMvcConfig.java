package com.shopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurer : 스프링 MVC에서 웹 애플리케이션 관련 설정을 하고자 할때 사용합니다.
// 용도 : 리소스 핸들링,CORS 설정 등등
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}") // application.properties 파일의 uploadPath 항목을 읽어서 변수에 저장합니다.
    String uploadPath ; // file:///c:/shop/


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 인터넷 주소 요청시 `/images` 또는 `/image`라는 요청이 들어 오면 uploadPath라는 경로에서 해당 데이터를 찾아서 읽어 들이겠습니다.
        registry
                .addResourceHandler("/images/**", "/image/**")
                .addResourceLocations(uploadPath);
    }
}
