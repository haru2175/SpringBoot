package com.shopping.entity;

import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product extends BaseEntity{
    @Id
    @Column(name = "product_id")
    // AUTO는 JPA 구현체가 자동으로 기본키 생성 전략을 결정합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ; // 상품 코드(기본 키역할)

    // 테이블에서 not null 옵션과 동일합니다.
    // 최대 길이는 50인데, 필수 입력 사항입니다.
    @Column(nullable = false, length = 50)
    private String name ; // 상품 이름

    @Column(nullable = false, name = "price")
    private int price ; // 가격

    @Column(nullable = false)
    private int stock ; // 재고 수량

    @Lob // CLOB(Character Large OBject), BLOB(Binary Large OBject)
    @Column(nullable = false)
    private String description ; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus ; // 상품 판매 상태

    @Enumerated(EnumType.STRING)
    private Category category ;

    @Column(nullable = false, length = 255)
    private String image01 ;
    private String image02 ;
    private String image03 ;


}
