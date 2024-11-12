package com.shopping.repository;

import com.shopping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //Member 엔터티에 'unique = true' 라고 설정이 되어있습니다.
    //이메일을 사용하여 회원을 검색하기 위한 '쿼리 메서드'
    //데이터베이스 연동(dao)
    Member findByEmail(String email);



}
