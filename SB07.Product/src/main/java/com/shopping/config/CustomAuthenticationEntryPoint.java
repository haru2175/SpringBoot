package com.shopping.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증(Authentication)을 받지 못했을 때, 수행할 커스텀 로직을 정의합니다.
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(authException != null){
            String message = "로그인이 필요합니다.";
            System.out.println(message);
            response.sendRedirect("/member/login");

        }else if(response.getStatus() == HttpServletResponse.SC_FORBIDDEN){
            String message = "요청하신 정보를 이용할 수 없습니다.";
            System.out.println(message);
            response.sendRedirect("/");
        }
    }
}
