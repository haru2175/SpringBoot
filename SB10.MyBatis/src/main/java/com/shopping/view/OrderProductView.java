package com.shopping.view;

import com.shopping.entity.OrderProduct;
import lombok.Getter;
import lombok.Setter;

// 주문한 상품 1개에 대한 정보를 저장하고 있는 클래스입니다.
@Getter
@Setter
public class OrderProductView {
    private String name ;
    private int count ;
    private int price ;
    private String image01 ;

    public OrderProductView(OrderProduct op, String image01) {
        this.name = op.getProduct().getName() ;
        this.count = op.getCount();
        this.price = op.getPrice();
        this.image01 = image01 ;
    }
}
