package com.shopping.repository;

import com.shopping.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {
    // 카트 id 와 상품 id 정보를 이용하여 `장바구니 상품` 엔터티에 담아 줍니다.
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);


}
