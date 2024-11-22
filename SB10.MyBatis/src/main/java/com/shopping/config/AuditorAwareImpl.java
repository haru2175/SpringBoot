package com.shopping.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// 감사(Auditor) : 감사 로그, 사용자 정보 추척, 로그인 사용자 정보 확인 등등
// 데이터 생성자, 수정자, 생성일자, 수정일자를 기록
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Authentication : 현재 인증된 사용자에 대한 정보 확인, 권한 관리
        // SecurityContextHolder : 현재 접속자의 보안 관련 설정 정보를 저장하고 관리해 줍니다.

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication() ;

        String userId = "" ; //

        if(authentication != null){
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
