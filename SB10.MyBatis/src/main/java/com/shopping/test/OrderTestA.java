package com.shopping.test;

import com.shopping.common.EntityMapping;
import com.shopping.common.GenerateData;
import com.shopping.entity.Order;
import com.shopping.entity.OrderProduct;
import com.shopping.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/*
Order 엔터티의 CascadeType.ALL 옵션에 대하여 2가지 테스트를 진행합니다.
CascadeType.ALL 옵션 사용시 OrderProduct을 저장하는 코드가 없지만, 연쇄 반응으로 인하여
3건의 데이터도 동시에 인서트가 되어야 합니다.
* */

@SpringBootTest
@Transactional
public class OrderTestA extends EntityMapping{
    @Test // 주문 1건에 상품 3개를 담아서 주문 테스트를 진행하면서 영속성 전이에 대한 점검을 해보겠습니다.
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Order order = new Order(); // 주문 정보를 저장하고 있는 객체
        int size = 3 ; // 주문하고자 하는 품목 개수

        for (int i = 0; i < size; i++) {
            Product product = GenerateData.createProduct(true, i) ;  // 상품 1개
            super.pr.save(product);

            OrderProduct op = new OrderProduct();  // 주문할 상품의 세부 정보
            op.setProduct(product);
            op.setCount(10);
            op.setOrder(order);
            op.setPrice(1000);

            // 주문 객체에 '주문 상품' 정보를 담아 줍니다.
            order.getOrderProducts().add(op);
        }

        // saveAndFlush() 메소드는 강제로 flush를 호출하여 영속성 컨텍스트의 모든 객체 정보들을 데이터 베이스에 반영해 줍니다.
        super.or.saveAndFlush(order) ;

        Long invoice = order.getId();
        System.out.println("송장 번호 : " + invoice);

        Order savedOrder = or.findById(invoice).orElseThrow(EntityNotFoundException::new);
        System.out.println("savedOrder info");
        System.out.println(savedOrder);

    }
}
