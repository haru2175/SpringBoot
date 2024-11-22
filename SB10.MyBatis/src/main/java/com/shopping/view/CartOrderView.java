package com.shopping.view;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CartOrderView {
    private Long cartProductId;

    // 장바구니 목록에서 여러 개의 상품을 동시에 주문할 수 있으므로 List 컬렉션이 되어야 합니다.
    private List<CartOrderView> cartOrderViewList;
}
