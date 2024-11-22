package com.shopping.entity;

import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import com.shopping.exception.OutOfStockException;
import com.shopping.view.ProductView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter @Setter
public class Product extends BaseEntity {
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

//    private String createdBy ;
//    private LocalDateTime regDate ; // 등록 시간

    // 화면에서 넘겨진 정보(ProductView)를 이용하여 엔터티(Product) 정보를 변경합니다.
    public void updateProduct(ProductView pv){
        this.name = pv.getName() ;
        this.price = pv.getPrice() ;
        this.stock = pv.getStock();
        this.description = pv.getDescription() ;
        this.productStatus = pv.getProductStatus() ;
        this.category = pv.getCategory() ;

        String imagePath = "/images/image";
        this.image01 = imagePath + pv.getImage01() ;
        this.image02 = imagePath + pv.getImage02() ;
        this.image03 = imagePath + pv.getImage03() ;

        super.setCreatedBy(pv.getCreatedBy());
    }

    // 상품을 주문하는 경우 재고 수량을 감소 시켜 주는 메소드입니다.
    public void removeStock(int vstock){
        int resStock = this.stock - vstock ; // 주문 이후 남은 재고 수량
        if(resStock < 0){
            String message = "상품의 재고 수량이 부족합니다. (현재 재고 수량" + this.stock + ")" ;
            throw new OutOfStockException(message);
        }

        // 주문 성공 이후 남은 재고 수량을 갱신합니다.
        this.stock = resStock ;
    }

    // 주문 내역 취소하기 시작
    // 주문 내역을 취소하는 경우 상품의 재고 수량을 늘어 나야 합니다.
    public void addStock(int vsotck){
        this.stock += vsotck;
    }

    // 주문 내역 취소하기 끝
}
