package com.shopping.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 이 파일은 스프링 설정 파일로 사용하겠습니다.
@EnableJpaAuditing // Jpa Auditing 기능을 사용하겠습니다.
public class AuditConfig {
    // Audit를 사용하여 등록한 사람, 시간, 수정한 사람, 시간 등을 처리해 줍니다.
    @Bean // AuditorAware 구현체를 Bean으로 등록시킵니다.
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }
}
