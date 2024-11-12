package com.shopping.repository;


import com.shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//<Product, Long> :
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByName(String name);
    List<Product> findByPriceLessThan(Integer price);
    List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);

    //Jpql
    @Query(value="SELECT i FROM Product i WHERE i.description LIKE " +
            "%:description% ORDER BY i.price DESC ")
    List<Product> findByProductDetail01(@Param("description") String description);

    //Sql
    @Query(value="SELECT * FROM Products WHERE products.description LIKE " +
            "%:description% ORDER BY products.price DESC ", nativeQuery = true)
    List<Product> findByProductDetail02(@Param("description") String description);

}
