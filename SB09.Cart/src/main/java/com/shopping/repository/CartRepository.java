package com.shopping.repository;

import com.shopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 참고) 회원과 카트는 일대일 연관 관계 매핑을 하고 있습니다.
    // 로그인한 회원이 소유하고 있는 카트 정보를 조회합니다.
    Cart findByMemberId(Long memberId);
}
