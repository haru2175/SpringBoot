package com.shopping.repository;

import com.shopping.entity.Product;
import com.shopping.view.MainView;
import com.shopping.view.SearchView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// QueryDsl을 사용하기 위한 사용자 정의 Resository 입니다.
public interface ProductRepositoryCustom {
    // Page 인터페이스는 database에서 페이징된 결과를 표현하기 위하여 사용됩니다.
    // import org.springframework.data.domain.Pageable;

    // 관리자가 보기 위한 상품 목록 보기 기능의 구현체 메소드
    Page<Product> getAdminProductPage(SearchView sv, Pageable pageable);

    // 메인 페이지에서 상품 목록을 보여 주기 위한 추상 메소드입니다.
    // sv : 상품에 대한 검색 조건, pageable : 페이징 정보를 저장하고 있는 객체
    Page<MainView> getMainProductPage(SearchView sv, Pageable pageable);
    
}
