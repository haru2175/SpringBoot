package com.shopping.service;

import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.OrderProduct;
import com.shopping.entity.Product;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.view.OrderHistoryView;
import com.shopping.view.OrderProductView;
import com.shopping.view.OrderView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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
    
    // 과거 주문 내역 조회 시작
    public Page<OrderHistoryView> getOrderList(String email, Pageable pageable){
        // 로그인 한 사람의 과거 주문 내역을 조회합니다.
        List<Order> orders = or.getOrderList(email,pageable);

        // 총 주문 건수를 구합니다.
        Long totalCount = or.getOrderSize(email);

        // 주문 내역 목록을 저장할 변수입니다.
        List<OrderHistoryView> orderHistoryViews = new ArrayList<>();

        // 주문한 내역을 반복합니다.
        for(Order ord:orders){
            // 주문 1개에 대한 OrderHistoryView 객체를 ohv를 정의합니다.
            OrderHistoryView ohv = new OrderHistoryView(ord);

            // 주문 1건에는 여러 개의 품목들이 포함됩니다.
            List<OrderProduct> orderProducts = ord.getOrderProducts();

            // 주문 상품들에 대한 반복문을 사용하여
            for (OrderProduct op:orderProducts){
                // OrderProductView 객체 opv를 구합니다.
                OrderProductView opv = new OrderProductView(op, op.getProduct().getImage01());
                ohv.addOrderProductView(opv); // ohv 객체에 opv 객체를 저장합니다.
            }

            orderHistoryViews.add(ohv);

        }

        return new PageImpl<OrderHistoryView>(orderHistoryViews,pageable,totalCount);
    }

    // 과거 주문 내역 조회 끝

    // 주문 내역 취소하기 시작
    // 주문(송장) 번호를 이용하여 `주문 취소`를 요청합니다.
    public void cancelOrder(Long orderId){
        // 주문 번호를 이용하여 주문 정보를 추출합니다.
        Order order = or.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
        or.save(order); // 데이터 베이스에 반영

    }


    // 로그인 한 사람의 이메일과 해당 주문의 이메일 정보를 비교합니다.(컨트롤러에서 호출함)
    public boolean validateOrder(Long orderId, String email){
        Member loginfo = mr.findByEmail(email) ; // 로그인 한 사람

        Order order = or.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member buyer = order.getMember() ; // 주문한 사람

        if(StringUtils.equals(loginfo.getEmail(), buyer.getEmail())){
            return true ;

        }else{
            return false ;
        }
    }

    // 주문 내역 취소하기 끝

    // 장바구니 목록 중에서 몇 개만 주문하기 시작
    // 장바구니 목록 중에서 체크한 상품 데이터들을 전달 받아서 주문을 하는 로직입니다.
    public Long orderInCartList(List<OrderView> orderViewList,String email){
        // orderViewList : 상품의 id와 주문 수량을 저장하고 있는 객체들의 모음입니다.
        // email : Security 인증을 통하여 흭득한 사용자 이메일 정보입니다.
        Member member = mr.findByEmail(email);

        // orderProductList : 이번에 주문하고자 하는 상품들의 리스트입니다.
        List<OrderProduct> orderProductList = new ArrayList<>();

        for(OrderView ov : orderViewList){
            Long productId = ov.getProductId();
            Product product = pr.findById(productId).orElseThrow(EntityNotFoundException::new);
            int count = ov.getCount();
            OrderProduct op = OrderProduct.createOrderProduct(product,count);
            orderProductList.add(op);
        }

        // order : 주문하고자 하는 객체 정보입니다.
        Order order = Order.createOrder(member,orderProductList);
        or.save(order); // 데이터 베이스에 저장
        return order.getId();

    }
    // 장바구니 목록 중에서 몇 개만 주문하기 끝
}
