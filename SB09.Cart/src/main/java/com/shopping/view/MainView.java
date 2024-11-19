package com.shopping.view;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainView {
    private Long id ;
    private String name ;
    private String description ;
    private String image01 ;
    private Integer price ;

    @QueryProjection // Projection은 테이블의 특정 정보들을 조회하는 동작을 의미합니다.
    // 조회된 결과들을 사용하여 화면 영역(view)으로 변환해 줍니다.
    // queryDsl에서 사용할 예정이므로, maven compile을 반듯시 실행하도록 합니다.
    public MainView(Long id, String name, String description, String image01, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image01 = image01;
        this.price = price;
    }
}
