package com.shopping.config;

import com.shopping.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 이 클래스는 설정용 파일로 사용할 겁니다.
@EnableWebSecurity // Spring Security를 활성화 시켜주는 어노테이션입니다.
public class SecurityConfig { // 시큐리티를 위한 설정 파일

    @Autowired // MemberService를 스프링이 자동으로 주입하도록 설정
    private MemberService ms;

    /**
     * SecurityFilterChain 빈을 설정하는 메소드
     *
     * @param http HttpSecurity 객체를 통해 보안 설정을 구성
     * @param authenticationManager AuthenticationManager를 설정
     * @return SecurityFilterChain 객체
     * @throws Exception 예외가 발생할 경우
     */
    @Bean // 인증, 인가, 로그인 등의 처리를 할 예정입니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable(); // CSRF 기능 비활성화
        
        http.cors() ; // CORS 허용

        http
            .formLogin() // 폼 기반 로그인 설정
            .loginPage("/member/login") // 커스텀 로그인 페이지 설정
            .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트할 URL 설정
            .usernameParameter("email") // 사용자 이름으로 사용할 파라미터 이름 설정
            .failureUrl("/member/login/error") // 로그인 실패 시 리다이렉트할 URL 설정
            .and()
            .logout() // 로그아웃 설정
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 요청 경로 설정
            .logoutSuccessUrl("/"); // 로그아웃 성공 시 리다이렉트할 URL 설정

        // HttpSecurity에 AuthenticationManager 설정
        http.authenticationManager(authenticationManager);

        // 인가(Authorization) 설정
        http.authorizeRequests()
            .mvcMatchers("/", "/member/**", "/product/**", "/images/**", "/thymeleaf/**", "/react/**").permitAll()
            .mvcMatchers("/admin/**", "/admin/product/**").hasRole("ADMIN")
            .anyRequest().authenticated() ;

        // 인증을 받지 못하면 커스텀 로직이 수행됩니다.
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        // 보안 필터 체인 빌드
        return http.build();
    }

    @Bean // static 디렉토리의 하위 파일은 인증을 무시하도록 합니다.
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    /**
     * AuthenticationManager 빈을 설정하는 메소드
     *
     * @param authenticationConfiguration AuthenticationConfiguration 객체를 통해 설정
     * @return AuthenticationManager 객체
     * @throws Exception 예외가 발생할 경우
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // AuthenticationConfiguration에서 AuthenticationManager를 가져와 리턴
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 커스텀 AuthenticationManager 빈을 설정하는 메소드
     *
     * @return AuthenticationManager 객체
     * @throws Exception 예외가 발생할 경우
     */
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        // DaoAuthenticationProvider를 사용하여 사용자 인증 설정
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(ms); // 사용자 상세 서비스를 MemberService로 설정
        authProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 인코더 설정

        // ProviderManager를 사용하여 AuthenticationManager 생성
        ProviderManager authenticationManager = new ProviderManager(authProvider);
        return authenticationManager; // 커스텀 AuthenticationManager 리턴
    }

    /**
     * 비밀번호 인코더 빈을 설정하는 메소드
     *
     * @return PasswordEncoder 객체
     */
    @Bean("passwordEncoder") // 비밀 번호 암호화를 위한 Bean입니다.
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder를 사용하여 비밀번호 인코딩
        return new BCryptPasswordEncoder();
    }
}
