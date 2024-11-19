package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
@Getter
@Setter
public class OrderProduct extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_id")
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order ;

    private int price ; // 제품 단가
    private int count ; // 구매 수량

    // 주문 기능 구현하기 시작
    // 상품과 구매 수량을 이용하여 OrderProduct 객체를 생성하고, 이를 반환해 줍니다.
    public static OrderProduct createOrderProduct(Product product, int count){
        OrderProduct op = new OrderProduct();
        op.setProduct(product);
        op.setCount(count);
        op.setPrice(product.getPrice());

        product.removeStock(count); // 재고 수량 감소

        return op;
    }

    // 해당 상품의 판매 금액을 반환합니다.
    public int getTotalPrice(){
        return price * count;
    }
    // 주문 기능 구현하기 끝

    // 주문 내역 취소하기 시작
    // 이전 주문 내역을 취소하는 경우 해당 상품의 재고 수량을 다시 늘려 주는 메소드입니다.
    public void cancel(){
        // count은 `구매 수량`을 의미하는 instance 변수로 정의되어 있습니다.
        this.getProduct().addStock(count);
    }
    // 주문 내역 취소하기 끝

}
