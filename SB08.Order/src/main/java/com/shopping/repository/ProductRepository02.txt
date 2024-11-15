package com.shopping.repository;

import com.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// <Product, Long> : 상품 리포지터리는 기본키가 Long 타입인 Product 엔터티를 다루는 리포지터리입니다.
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    List<Product> findProductByName(String name);

    List<Product> findByPriceLessThan(Integer price);

    List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);

    // Jpql
    // :description는 구문 외부에서 입력해 주어야 합니다.
    // @Param 어노테이션을 이용하여 대입해 주어야 합니다.
    @Query(value = "select i from Product i where i.description like " +
        "%:description% order by i.price desc ")
    List<Product> findByProductDetail01(@Param("description") String description);

    // Sql
    @Query(value = "select * from Products i where i.description like " +
            "%:description% order by i.price desc ", nativeQuery = true)
    List<Product> findByProductDetail02(@Param("description") String description);

}
