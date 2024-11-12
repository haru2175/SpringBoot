package com.shopping.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import com.shopping.entity.Product;
import com.shopping.entity.QProduct;
import com.shopping.view.SearchView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{
    private JPAQueryFactory queryFactory ;

    public ProductRepositoryCustomImpl(EntityManager em ) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override // 관리자가 보기 위한 상품 목록 보기 기능의 구현체 메소드
    public Page<Product> getAdminProductPage(SearchView sv, Pageable pageable) {
        QueryResults<Product> results =
                this.queryFactory
                        .selectFrom(QProduct.product)
                        .where(
                                dateRange(sv.getSearchDateType()),
                                productStatusCondition(sv.getProductStatus()),
                                chooseCategory(sv.getCategory()),
                                searchByCondition(sv.getSearchMode(),sv.getSearchKeyword())

                        ) // ...은 가변성
                        .orderBy(QProduct.product.id.desc())
                        .offset(pageable.getOffset()) // 초기 시점 mysql 옵션
                        .limit(pageable.getPageSize())  // 페이지 사이즈 1p,2p,3p 등등
                        .fetchResults();

        List<Product> content = results.getResults();
        long total = results.getTotal();


        return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression productStatusCondition(ProductStatus productStatus) {
        // 상품의 판매 상태(판매, 품절)와 동일한 값만 반환합니다.
        if(productStatus==null || productStatus == ProductStatus.NOTHING){
            return null ;
        }else{
            return QProduct.product.productStatus.eq(productStatus) ;
        }
    }

    private BooleanExpression chooseCategory(Category category) {
        if(category==null || category == Category.NOTHING){
            return null;
        }else{
            return QProduct.product.category.eq(category) ;
        }
    }
    private BooleanExpression searchByCondition(String searchMode, String searchKeyword) {
        String likeString = "%" + searchKeyword + "%"; // 데이터 베이스 like 연산자와 관련

        if(StringUtils.equals("name", searchMode)) { // 상품 이름으로 검색
            return QProduct.product.name.like(likeString);

        }else if(StringUtils.equals("createdBy", searchMode)){ // 상품 등록자로 검색하기
            return QProduct.product.createdBy.like(likeString);

        }else{
            return null;
        }
    }

    // import com.querydsl.core.types.dsl.BooleanExpression;
    private BooleanExpression dateRange(String searchDateType) {
        // 검색 기간의 범위를 지정합니다.
        LocalDateTime dateTime = LocalDateTime.now() ;

        if(StringUtils.equals("all", searchDateType) || searchDateType==null){
            return null ; // 전체 기간 검색

        }else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1); // 날

        }else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1); // 주

        }else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1); // 월

        }else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6); // 월
        }

        return QProduct.product.regDate.after(dateTime) ;
    }
}
