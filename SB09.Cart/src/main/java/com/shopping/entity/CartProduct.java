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

    // CartProduct02 장바구니에 담기 시작
    // xx 카트에 yy 상품을 count 개 담기 위한 CartProduct를 생성합니다.
    public static CartProduct createCartProduct(Cart cart, Product product, int count){
        CartProduct cp = new CartProduct();
        cp.setCart(cart);
        cp.setProduct(product);
        cp.setCount(count);
        return cp ;
    }

    // 장바구니에 들어 있는 해당 상품의 수량을 누적시킵니다.
    public void addCount(int count){
        this.count += count ;
    }
    // CartProduct02 장바구니에 담기 끝

}
