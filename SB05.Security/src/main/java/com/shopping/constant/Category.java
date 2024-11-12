package com.shopping.constant;

// 상품의 유형을 의미하는 열거형 타입
public enum Category {
    NOTHING("-- 선택해 주세요."), BREAD("빵"), BEVERAGE("음료수"), FRUIT("과일"), CAKE("케익"), WINE("와인"), MACARON("마카롱") ;

    private String korean ;

    private Category(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}
