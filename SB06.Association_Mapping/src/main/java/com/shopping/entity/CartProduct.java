package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart_products")
@Getter
@Setter
@ToString
public class CartProduct extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "cart_product_id")
    private Long id; // 카트 상품의 아이디
    private int count ; // 구매 수량

    @ManyToOne(fetch = FetchType.LAZY)  // 나의 카트 상품에 물건들을 여러개 담을 수 있습니다.
    @JoinColumn(name = "cart_id") // Cart 엔터티의 pk 컬럼 이름을 적어 주세요.
    private Cart cart ;

    @ManyToOne(fetch = FetchType.LAZY)  // 모든 물건들은 누구든지 구매 가능합니다.
    @JoinColumn(name = "product_id") // Product 엔터티의 pk 컬럼 이름을 적어 주세요.
    private Product product ;
}
