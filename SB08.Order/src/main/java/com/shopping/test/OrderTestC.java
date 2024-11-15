package com.shopping.test;

import com.shopping.common.EntityMapping;
import com.shopping.entity.Order;
import com.shopping.entity.OrderProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@SpringBootTest
@Transactional
public class OrderTestC extends EntityMapping {
    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = super.createOrder();

        // 1번째 상품의 아이디 정보
        Long productId = order.getOrderProducts().get(0).getId() ;

        super.em.flush(); // 영속성 컨텍스트의 내용을 Database에 반영시킵니다.
        super.em.clear(); // 영속성 컨텍스트의 내용을 비웁니다.

        OrderProduct op = super.opr.findById(productId).orElseThrow(EntityExistsException::new) ;
        op.getOrder().getOrderDate();
        System.out.println("================================");
    }
}
