package com.shopping.repository;

import com.shopping.entity.CartProduct;
import com.shopping.view.CartDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    // 카트 id 와 상품 id 정보를 이용하여 `장바구니 상품` 엔터티에 담아 줍니다.
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);

    // 장바구니 목록 조회하기 시작
    // 주의) JPQL( 엔터티 객체) 사용시 Table 이름이 아니고, Entity 이름을 작성해야 합니다. (대소문자 구분)
    @Query(" select new com.shopping.view.CartDetailView(cp.id, i.name, i.price, cp.count, p.image01)" +
            " from CartProduct cp, Product p " +
            " join cp.product i" +
            " where cp.cart.id = :cartId" +
            " and p.id = cp.product.id")
    List<CartDetailView> findCartDetailViewList(Long cartId);



    // 장바구니 목록 조회하기 끝
}
