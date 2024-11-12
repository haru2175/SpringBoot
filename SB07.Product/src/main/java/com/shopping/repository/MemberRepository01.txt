package com.shopping.repository;

import com.shopping.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // Member 엔터티에 `unique = true`라고 설정이 되어 있습니다.
    // 이메일을 사용하여 회원을 검색하기 위한 `쿼리 메소드`입니다.
    Member findByEmail(String email);
}
