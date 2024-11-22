package com.shopping.test;

import com.shopping.common.EntityMapping;
import com.shopping.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 엔터티 관리자를 이용하여 트랜잭션을 처리하는 경우 반드시 해당 어노테이션을 사용해 주어야 합니다.
public class OrderTestB extends EntityMapping {
    @Test
    @DisplayName("고아 객체 테스트")
    public void orphanRemovalTest(){
        Order order = super.createOrder() ;

        // Order 엔터티에서 orphanRemoval = true 옵션을 사용했습니다.
        // 따라서, 이 테스트를 수행하면 콘솔 창에 `delete` 구문이 1개 보이면 테스트가 성공한 것으로 보면 됩니다.
        order.getOrderProducts().remove(0) ; // 품목 1개 삭제
        super.em.flush();
    }
}
