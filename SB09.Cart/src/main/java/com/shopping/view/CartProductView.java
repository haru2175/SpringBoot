package com.shopping.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 상품의 아이디와 담을 수량을 보여 주는 클래스입니다.
// 사용자가 '장바구니 담기' 버튼을 클릭할 때 사용됩니다.
@Getter @Setter
public class CartProductView {
    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long productId;

    @Min(value = 1, message = "수량은 최소 1개 이상 담아 주세요.")
    private int count ;
}
