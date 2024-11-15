package com.shopping.constant;

// 상품의 현재 상태를 의미하는 열거형 타입
public enum ProductStatus {
    NOTHING("-- 선택해 주세요."), SELL("판매중"), SOLD_OUT("품절") ;

    private String status ;

    private ProductStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
