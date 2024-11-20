package com.shopping.service;

import com.shopping.entity.Cart;
import com.shopping.entity.CartProduct;
import com.shopping.entity.Member;
import com.shopping.entity.Product;
import com.shopping.repository.CartProductRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.view.CartProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CartService {
    // 장바구니에 담기 시작
    private final MemberRepository mr ;
    private final CartRepository cr;
    private final ProductRepository pr;
    private final CartProductRepository cpr ;

    public Long addCart(CartProductView cpv, String email){
        // cpv : 상품 상세 페이지에서 넘겨진 정보(상품id, 구매 수량)
        // email : 로그인한 사람의 이메일 정보
        Member member = mr.findByEmail(email) ;
        Long memberId = member.getId() ;

        Cart cart = cr.findByMemberId(memberId);
        if(cart == null){ // 카트가 존재하지 않는 경우
            cart = Cart.createCart(member) ; // 새 카트 준비
            cr.save(cart); // 데이터 베이스에 저장
        }

        Long cartId = cart.getId() ;
        Long productId = cpv.getProductId() ;

        System.out.println(cartId + "/" + productId);

        CartProduct savedCartProduct = cpr.findByCartIdAndProductId(cartId, productId );

        int count = cpv.getCount() ; // `장바구니 상품` 엔터티에 담을 수량

        if(savedCartProduct != null){  // 해당 상품이 카트에 이미 담겨져 있는 경우
            // 기존 상품에 수량을 누적시킵니다.
            savedCartProduct.addCount(count);

        }else{ // 해당 상품을 카트에 처음 담는 경우
            Product product = pr.findById(productId).orElseThrow(EntityNotFoundException::new);

            savedCartProduct = CartProduct.createCartProduct(cart, product, count);
        }

        cpr.save(savedCartProduct) ;

        return savedCartProduct.getId();
    }

    // 장바구니에 담기 끝
}
