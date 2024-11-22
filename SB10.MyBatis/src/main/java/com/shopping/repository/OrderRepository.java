package com.shopping.repository;

import com.shopping.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 현재 로그인한 회원의 주문 정보를 최신 주문 일자부터 조회해 줍니다. // Order 엔터티 JPQL
    @Query("select o from Order o"+
            " where o.member.email = :email"+
            " order by o.orderDate desc ")
    List<Order> getOrderList(@Param("email")String email, Pageable pageable);

    // 현재 로그인한 회원의 총 주문 건수를 조회합니다.
    @Query(" select count(o) from Order o " +
           " where o.member.email = :email ")
    Long getOrderSize(@Param("email")String email);
}
