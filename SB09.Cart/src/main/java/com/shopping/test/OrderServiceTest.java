package com.shopping.test;

import com.shopping.common.GenerateData;
import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.Product;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.service.OrderService;
import com.shopping.view.OrderView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/*
주의 사항
    테스트시 지연 로딩 때문에 @Transactional 어노테이션을 명시해 주어야 합니다.
    상품 번호는 반드시 테이블에 존재하는 상품 번호를 기입해야합니다.
*/

@SpringBootTest
@Transactional // import org.springframework.transaction.annotation.Transactional;
public class OrderServiceTest {
    @Autowired
    private ProductRepository pr ;

    @Autowired
    private MemberRepository mr ;

    @Autowired
    private OrderService os ;

    @Autowired
    private OrderRepository or ;

    @Test
    @DisplayName(("주문하기 테스트"))
    public void order(){
        // 상품 번호 productId으로 count 개수 만큼 주문합니다.

        Long productId = 5L ; // 데이터 베이스에 저장된 임의의 상품 번호
        int count = 10 ; // 주문할 갯수

        Product product = pr.findById(productId).orElseThrow(EntityNotFoundException::new) ;

        Member member = this.saveMember() ;

        OrderView ov = new OrderView();
        ov.setProductId(productId);
        ov.setCount(count);

        System.out.println(member == null);
        System.out.println(os == null);

        // orderId는 송장 번호입니다.
        Long orderId = os.order(ov, member.getEmail());

        Order order = or.findById(orderId).orElseThrow(EntityNotFoundException::new) ;

        int totalPrice = ov.getCount() * product.getPrice() ;

        System.out.println("주문한 상품의 총가격 : " + totalPrice);
        System.out.println("데이터 베이스 내 상품 가격 : " + order.getTotalPrice());
    }

    private Member saveMember() {
        Member member = new Member();
        member.setEmail("sample3@naver.com");
        return mr.save(member) ;
    }

    // 주문 취소하기 시작
    @Test
    @DisplayName("주문 취소하기 테스트")
    public void cancelOrder(){
        Member member = GenerateData.createMember() ; // 회원 객체를 구합니다.
        mr.save(member) ;

        String email = member.getEmail() ; // 메일 주소 가져오고

        Product product = GenerateData.createProduct(true, 0);
        pr.save(product) ; // 상품을 가져오고

        // 내가 주문했던 정보
        OrderView ov = new OrderView();
        ov.setCount(10);
        ov.setProductId(product.getId());

        Long orderId = os.order(ov, email); // 주문 하기
        System.out.println("주문 아이디 : " + orderId);

        Order order = or.findById(orderId).orElseThrow(EntityNotFoundException::new);

        order.cancelOrder(); // 주문 취소하기

        System.out.println("주문 아이디(from Database)) : " + order.getId());
        System.out.println("주문 상태 : " + order.getOrderStatus());
    }
    // 주문 취소하기 끝
}
