package com.shopping.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 상품 상세 보기 페이지에서 넘어 오는 id와 수량 정보를 처리해주는 command 객체입니다.
@Setter @Getter
public class OrderView {
    @NotNull(message = "상품의 아이디는 필수 입력 사항입니다.")
    private Long productId ;

    @Min(value = 1, message = "최소 주문량은 1개입니다.")
    @Max(value = 999, message = "최대 주문량은 999개입니다.")
    private int count ;
}
