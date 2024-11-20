package com.shopping.view;

import lombok.Getter;
import lombok.Setter;

// 장바구니 목록에 보이는 상품 1개에 대한 정보를 표현하는 클래스입니다.
@Getter @Setter
public class CartDetailView {
    private Long cartProductId ; // 장바구니 상품의 아이디
    private String name ;
    private int price ;
    private int count ;
    private String image01 ;

    public CartDetailView(Long cartProductId, String name, int price, int count, String image01) {
        this.cartProductId = cartProductId;
        this.name = name;
        this.price = price;
        this.count = count;
        this.image01 = image01;
    }
}
