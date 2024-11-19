package com.shopping.entity;

import com.shopping.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id ; // 송장 번호(invoice number)

    private LocalDateTime orderDate ;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus ;

    @ManyToOne(fetch = FetchType.LAZY)  // 고객 1명은 여러번의 주문을 할 수 있습니다.
    @JoinColumn(name = "member_id")
    private Member member ;

    // 양방향 매핑을 위한 코드 작성
    // mappedBy 속성의 값은 맞은 편 Entity의 변수 이름을 명시하면 됩니다.
    // cascade : 영속성 전이 관련 옵션
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>() ;


    // 주문 기능 구현하기 시작
    // Order과 OrderProduct는 양방향 연관 관계를 맺고 있습니다.
    // 이번 주문의 모든 상품들에 대하여 총 주문 금액을 구해줍니다.
    public int getTotalPrice(){
        int totalPrice = 0 ; // 모든 상품의 총 주문 금액

        for(OrderProduct bean:this.orderProducts){
            totalPrice += bean.getTotalPrice();
        }
        return totalPrice;
    }

    // 해당 주문의 '주문 상품' 목록에 추가합니다.
    private void addOrderProduct(OrderProduct op){
        this.orderProducts.add(op);
        op.setOrder(this); // 해당 '주문 상품'은 이 주문의 멤버 입니다.
    }

    // member : 주문자 정보, lists : 주문 상품 리스트
    public static Order createOrder(Member member, List<OrderProduct> lists){
        Order order = new Order(); // 주문 객체 생성
        order.setMember(member); // 해당 주문 정보를 현재 로그인 한 사람에게 할당

        for(OrderProduct bean : lists){
            order.addOrderProduct(bean);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order ;
    }
    // 주문 기능 구현하기 끝

    // 주문 내역 취소하기 시작
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderProduct op : this.orderProducts){
            op.cancel(); // 각 품목들에 대하여 재고 수량을 늘립니다.
        }
    }
    // 주문 내역 취소하기 끝
}
