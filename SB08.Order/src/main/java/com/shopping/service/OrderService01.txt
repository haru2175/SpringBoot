package com.shopping.service;

import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.OrderProduct;
import com.shopping.entity.Product;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.view.OrderView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    // 주문 기능 구현하기 시작
    private final ProductRepository pr ;
    private final MemberRepository mr ;
    private final OrderRepository or ;

    // 이메일 정보와 주문 정보(OrderView) 객체를 사용하여 주문에 대한 로직을 구현합니다.
    public Long order(OrderView ov, String email){
        Long productId = ov.getProductId(); // 요구자가 주문한 상품 번호
        // 상품 객체 구하기
        Product product = pr.findById(productId).orElseThrow(EntityNotFoundException::new);
        Member buyer = mr.findByEmail(email) ; // 주문자 정보

        int count = ov.getCount() ; // 요구자가 주문한 구매 수량
        OrderProduct op = OrderProduct.createOrderProduct(product, count);

        List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
        orderProductList.add(op) ;

        Order order = Order.createOrder(buyer, orderProductList) ;
        or.save(order) ; // 주문 객체를 데이터 베이스에 저장

        return order.getId();
    }
    // 주문 기능 구현하기 끝
}
