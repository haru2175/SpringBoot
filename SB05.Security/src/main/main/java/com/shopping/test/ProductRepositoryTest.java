package com.shopping.test;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shopping.common.GenerateData;
import com.shopping.constant.ProductStatus;
import com.shopping.entity.Product;
import com.shopping.entity.QProduct;
import com.shopping.repository.ProductRepository;
import org.assertj.core.api.Assertions;
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

@SpringBootTest //테스트코드 클래스 선언
public class ProductRepositoryTest {
    @Autowired //인스턴스 변수 값 주입
    ProductRepository productRepository;

    /*
     원활한 테스트 진행을 위하여 다음 문장을 수행한 다음 아래 코드를 실행해 주세요.
     delete from products;
     commit ;
 */
    @Test
    @DisplayName("query Dsl 을 위한 샘플 데이터 생성")
    public void createProductListNew() {
        String[] fruits = {"사과", "배", "오렌지", "파인애플"};
        String[] description = {"달아요.", "맛있어요.", "맛없어요.", "떫어요."};
        Integer[] stock = {100, 200, 300, 400, 500, 600};
        Integer[] price = {111, 222, 333, 444, 555};

        for (int i = 0; i < 30; i++) {
            Product product = new Product();

            product.setName(fruits[i % fruits.length]);
            product.setPrice(price[i % price.length]);
            product.setDescription(description[i % description.length]);
            if (i % 2 == 0) {
                product.setProductStatus(ProductStatus.SELL);
            } else {
                product.setProductStatus(ProductStatus.SOLD_OUT);
            }
            product.setImage01("sample.jpg");
            product.setStock(stock[i % stock.length]);
            product.setRegDate(LocalDateTime.now());

            Product savedItem = productRepository.save(product);
            System.out.println(savedItem.toString());
        }
    }


    @Test
    @DisplayName("특정 문구가 설명에 들어가는 상품 조회 - Query 애너테이션 사용 - 01")
    public void findByProductDetail01Test() {
        String description = "떫어";
        List<Product> itemList = productRepository.findByProductDetail01(description);

        for (Product product : itemList) {
            System.out.println(product);
        }

        Assertions.assertThat(itemList.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("특정 문구가 설명에 들어가는 상품 조회 - Query 애너테이션 사용 - 02")
    public void findByProductDetail02Test() {
        String description = "달아";
        List<Product> itemList = productRepository.findByProductDetail02(description);

        for (Product product : itemList) {
            System.out.println(product);
        }

        Assertions.assertThat(itemList.size()).isEqualTo(3);
    }


    //@PersistenceContext는 EntityManager를 주입하는 애너테이션
    @PersistenceContext //JPA가 동작하는 영속성 작업 공간
            EntityManager em;  //JPA에서 엔티티 객체를 관리하는 인터페이스. 데이터베이스에 대한 CRUD(Create, Read, Update, Delete) 작업을 수행

    @Test
    @DisplayName("query Dsl Test 01")
    public void queryDslTest01() {
        //JPAQueryFactory : 쿼리 문장을 실행해주는 Query Builder
        ////em을 인자로 받아 JPA의 EntityManager와 연결
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        String description = "어요";   //상품 설명 내부에서 찾고자 하는 단어
        QProduct qProduct = QProduct.product;

        JPAQuery<Product> query = queryFactory
                .selectFrom(qProduct)
                .where(qProduct.productStatus.eq(ProductStatus.SELL))
                .where(qProduct.description.like("%" + description + "%"))
                .orderBy(qProduct.price.desc());

        List<Product> itemList = query.fetch();

        Assertions.assertThat(itemList.size()).isEqualTo(7);
    }

    @Test
    @DisplayName("query Dsl Test 02")
    public void queryDslTest02() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        int price = 200;
        String description = "달아";   //상품 설명 내부에서 찾고자 하는 단어
        QProduct qProduct = QProduct.product;

        BooleanBuilder booleanBuilder = new BooleanBuilder();  //참 거짓 where절 조건 만드는 클래스
        booleanBuilder.and(qProduct.description.like("%"+description+"%"));
        booleanBuilder.and(qProduct.productStatus.eq(ProductStatus.SELL));
        booleanBuilder.and(qProduct.price.gt(price));

        //Paging Condition
        //of(0,2): 1페이지에 내용 2개만 표시
        Pageable pageable = PageRequest.of(0, 2);

        List<Product> itemList = queryFactory
                .selectFrom(qProduct)
                .where(booleanBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults()
                .getResults();

        Assertions.assertThat(itemList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createProductTest() {
        Product product = GenerateData.createProduct(true, 3);
        System.out.println(product);

        Product savedItem = productRepository.save(product);
        System.out.println("savedItem = " + savedItem);
    }

    @Test
    @DisplayName("상품 여러 개 저장하기")
    public void createProductTestMany() {
        for (int i = 0; i < 20; i++) {
            Product product = null;
            if (i % 2 == 0) {
                product = GenerateData.createProduct(true, i);
            } else {
                product = GenerateData.createProduct(false, i);
            }
            Product savedItem = productRepository.save(product);
        }
    }

    @Test
    @DisplayName("상품명 조회하기")
    public void findProductByNameTest() {
        List<Product> itemList = productRepository.findProductByName("사과");

        for (Product item : itemList) {
            System.out.println(item);
        }
    }

    //List<Product> findByPriceLessThan(Integer price);
    //List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);
    @Test
    @DisplayName("지정 가격보다 낮은 상품 조회")
    public void findByPriceLessThanTest() {
        Integer price = 5000;
        List<Product> itemList = productRepository.findByPriceLessThan(price);

//        for (Product item : itemList) {
//            System.out.println(item);
//        }

        Assertions.assertThat(itemList.size()).isEqualTo(5);
    }


    //SELECT * FROM products where price < 5000;
    //SELECT COUNT(*) AS count_of_rows FROM products WHERE price < 5000;
    @Test
    @DisplayName("지정 가격보다 낮은 상품을 내림차순 정렬하여 조회")
    public void findByPriceLessThanOrderByPriceDescTest() {
        Integer price = 5000;
        List<Product> itemList = productRepository.findByPriceLessThanOrderByPriceDesc(price);

//        for (Product item : itemList) {
//            System.out.println(item);
//        }

        Assertions.assertThat(itemList.size()).isEqualTo(5);
    }
}
