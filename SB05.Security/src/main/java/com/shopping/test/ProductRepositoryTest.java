package com.shopping.test;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.common.GenerateData;
import com.shopping.constant.ProductStatus;
import com.shopping.entity.Product;
import com.shopping.entity.QProduct;
import com.shopping.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired // 인스턴스 변수의 값을 주입해줍니다.
    ProductRepository productRepository ;

    /*
        원활한 테스트 진행을 위하여 다음 문장을 수행한 다음 아래 코드를 실행해 주세요.
        delete from products;
        commit ;
    */
    @Test
    @DisplayName("querydsl을 위한 샘플 데이터 생성")
    public void createProductListNew(){
        String[] fruits = {"사과", "배", "오렌지", "파인애플"};
        String[] description = {"달아요.", "맛있어요.", "맛없어요.", "떫어요."};
        Integer[] stock = {100, 200, 300, 400, 500, 600};
        Integer[] price = {111, 222, 333, 444, 555};


        for (int i = 0; i < 30; i++) {
            Product product = new Product();

            product.setName(fruits[i % fruits.length]);
            product.setPrice(price[i % price.length]);
            product.setDescription(description[i % description.length]);
            if(i%2 ==0){
                product.setProductStatus(ProductStatus.SELL);
            }else{
                product.setProductStatus(ProductStatus.SOLD_OUT);
            }
            product.setImage01("sample.jpg");
            product.setStock(stock[i % stock.length]);
            product.setRegDate(LocalDateTime.now());

            Product savedItem = productRepository.save(product) ;
            System.out.println(savedItem.toString());
        }
    }

    // @PersistenceContext는 EntityManager를 주입해주는 어노테이션입니다.
    @PersistenceContext // JPA가 동작하는 영속성 작업 공간
    EntityManager em ; // 엔터티 관리자

    /*
select * from products
where description like '%어요%'
and product_status='SELL'
order by price desc ;
    */
    @Test
    @DisplayName("query Dsl Test 01")
    public void queryDslTest01(){
        // queryFactory : 쿼리 문장을 실행해 주는 Query Builder
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        String description = "어요" ; // 찾고자 하는 단어
        QProduct qbean = QProduct.product;

        JPAQuery<Product> query = queryFactory
                .selectFrom(qbean)
                .where(qbean.productStatus.eq(ProductStatus.SELL))
                .where(qbean.description.like("%"+description+"%"))
                .orderBy(qbean.price.desc());

        List<Product> itemList = query.fetch() ;
        System.out.println("조회된 개수 : " + itemList.size());

        for(Product item:itemList){
            System.out.println(item);
        }
    }
    /*
select * from products
where description like '%달아%'
and product_status='SELL'
and price > 200 ;
     */
    @Test
    @DisplayName("query Dsl Test 02")
    public void queryDslTest02(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        int price = 200 ;
        String description = "달아" ;
        QProduct qbean = QProduct.product;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        // Filtering Condition : where 절과 유사한 개념
        booleanBuilder.and(qbean.description.like("%"+description+"%"));
        booleanBuilder.and(qbean.productStatus.eq(ProductStatus.SELL));
        booleanBuilder.and(qbean.price.gt(price));

        // Paging Condition
        // of(0, 2) : 1페이지에 내용 2개만 보여 주세요.
        Pageable pageable = PageRequest.of(0, 2);

        List<Product> itemList = queryFactory
                .selectFrom(qbean)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch() ;

        System.out.println("조회된 개수 : " + itemList.size());

        for(Product item:itemList){
            System.out.println(item);
        }
    }


    // select * from products where description like '%떫어%';
    @Test
    @DisplayName("@Query 어노테이션을 사용한 상품 조회 01")
    public void findByProductDetail01Test(){
        String description = "떫어" ;
        List<Product> itemList = productRepository.findByProductDetail01(description);
        System.out.println(itemList.size() + "개");
        for(Product item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("@Query 어노테이션을 사용한 상품 조회 02")
    public void findByProductDetail02Test(){
        String description = "아" ;
        List<Product> itemList = productRepository.findByProductDetail02(description);
        System.out.println(itemList.size() + "개");
        for(Product item : itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createProudctTest(){
        Product product = GenerateData.createProduct(true, 3);
        System.out.println(product);

        Product savedItem = productRepository.save(product) ;
        System.out.println(savedItem);
    }

    @Test
    @DisplayName("상품 여러 개 저장하기")
    public void createProductTestMany(){
        for (int i = 0; i < 20; i++) {
            Product product = null ;
            if(i%2==0){
                product = GenerateData.createProduct(true, i);
            }else{
                product = GenerateData.createProduct(false, i);
            }
            Product savedItem = productRepository.save(product);
        }
    }

    // select * from products where price < 5000 ;
    @Test
    @DisplayName("특정 상품 가격 이하만 조회하기")
    public void findByPriceLessThanTest(){
        Integer price = 4000 ;
        List<Product> itemList = productRepository.findByPriceLessThan(price);

        for(Product item : itemList){
            System.out.println(item);
        }
    }

    // select * from products where price < 5000 order by price desc ;
    @Test
    @DisplayName("특정 상품 가격 이하만 조회하되 내림차순 정려하기")
    public void findByPriceLessThanOrderByPriceDescTest(){
        Integer price = 4000 ;
        List<Product> itemList = productRepository.findByPriceLessThanOrderByPriceDesc(price);

        for(Product item : itemList){
            System.out.println(item);
        }
    }


    @Test
    @DisplayName("상품명 조회 테스트")
    public void findProductByNameTest(){
        String name = "사과";
        List<Product> itemList = productRepository.findProductByName(name);

        for(Product item : itemList){
            System.out.println(item);
        }
    }
}