package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "carts")
@Getter @Setter @ToString
public class Cart extends BaseEntity {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @OneToOne(fetch = FetchType.LAZY) // Cart 1개는 Member 1명과 `일대일 연관 관계`에 있습니다.
    @JoinColumn(name = "member_id") // foreign key
    private Member member ;

    // 장바구니에 상품 담기 시작
    public static  Cart createCart(Member member){
        Cart cart = new Cart();

        // 장바구니 한개는 특정 회원의 것입니다.(일대일 연관 관계 매핑)
        cart.setMember(member);
        return cart;
    }
    // 장바구니에 상품 담기 끝
}
