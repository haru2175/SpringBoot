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
}
