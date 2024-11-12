package com.shopping.view;

import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 관리자용 페이지에서 상품에 대한 필드 검색을 위한 command 객체입니다.
@Getter @Setter @ToString
public class SearchView {
    // 조회할 범위를 선정하기 위한 변수로, 현재 시각과 상품 등록일 비교하여 처리합니다.
    // all(전체 기간 ), 1d(하루),1w(1주일), 1m(한달), 6m(6개월)
    private String searchDateType ;

    // 상품의 `판매 상태`를 기준으로 상품 데이터를 조회합니다.
    private ProductStatus productStatus;


    private Category category; // 특정 카테고리만 조회하겠습니다.
    private String searchMode; // 상품 검색 모드(name : 상품 이름, createdBy : 상품 등록자 아이디)
    private String searchKeyword; // 검색 키워드

}
