package com.shopping.service;

import com.shopping.entity.Cart;
import com.shopping.entity.CartProduct;
import com.shopping.entity.Member;
import com.shopping.entity.Product;
import com.shopping.repository.CartProductRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.view.CartDetailView;
import com.shopping.view.CartProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    // 장바구니에 담기 시작
    private final MemberRepository mr;
    private final CartRepository cr;
    private final ProductRepository pr;
    private final CartProductRepository cpr;

    public Long addCart(CartProductView cpv, String email) {
        // cpv : 상품 상세 페이지에서 넘겨진 정보(상품id, 구매 수량)
        // email : 로그인한 사람의 이메일 정보
        Member member = mr.findByEmail(email);
        Long memberId = member.getId();

        Cart cart = cr.findByMemberId(memberId);
        if (cart == null) { // 카트가 존재하지 않는 경우
            cart = Cart.createCart(member); // 새 카트 준비
            cr.save(cart); // 데이터 베이스에 저장
        }

        Long cartId = cart.getId();
        Long productId = cpv.getProductId();

        System.out.println(cartId + "/" + productId);

        CartProduct savedCartProduct = cpr.findByCartIdAndProductId(cartId, productId);

        int count = cpv.getCount(); // `장바구니 상품` 엔터티에 담을 수량

        if (savedCartProduct != null) {  // 해당 상품이 카트에 이미 담겨져 있는 경우
            // 기존 상품에 수량을 누적시킵니다.
            savedCartProduct.addCount(count);

        } else { // 해당 상품을 카트에 처음 담는 경우
            Product product = pr.findById(productId).orElseThrow(EntityNotFoundException::new);

            savedCartProduct = CartProduct.createCartProduct(cart, product, count);
        }

        cpr.save(savedCartProduct);

        return savedCartProduct.getId();
    }

    // 장바구니에 담기 끝

    // 장바구니 목록 조회하기 시작
    @Transactional(readOnly = true) // 읽기 전용으로 하겠다. springframework
    public List<CartDetailView> getCartList(String email) {
        Member member = mr.findByEmail(email);
        Long memberId = member.getId();
        Cart cart = cr.findByMemberId(memberId);

        List<CartDetailView> cdvList = null;

        if (cart == null) { // 방금 카트를 준비한 경우 신규 카트 반환
            cdvList = new ArrayList<CartDetailView>();

        } else { // 이전 카트 반환
            cdvList = cpr.findCartDetailViewList(cart.getId());
        }

        return cdvList;
    }
    // 장바구니 목록 조회하기 끝

    // 장바구니 목록 조회 페이지 수량 변경 시작
    // 사용자가 상품 번호 cartProductId에 대하여 수량을 count로 변경하고자 합니다.
    public void updateCartProductCount(Long cartProductId, int count) {
        CartProduct cartProduct = cpr.findById(cartProductId).orElseThrow(EntityNotFoundException::new);
        cartProduct.updateCount(count); // 엔터티의 수량을 변경합니다.
        cpr.save(cartProduct); // 데이터 베이스의 값을 변경합니다.
    }

    // 다음 코드는 Controller 에서 사용될 예정이므로, 미리 선언해 둡니다.
    @Transactional(readOnly = true) // email 인 회원이 수정/삭제 권한이 있는 지 체크합니다.
    public boolean validateCartProduct(Long cartProductId, String email) {
        Member loginfo = mr.findByEmail(email); // 로그인한 사람
        System.out.println("cartProductId");
        System.out.println(cartProductId);
        CartProduct cartProduct = cpr.findById(cartProductId).orElseThrow(EntityNotFoundException::new);
        System.out.println("cartProduct==null");
        System.out.println(cartProduct == null);
        Member cartOwner = cartProduct.getCart().getMember(); // 카트 소유자

        System.out.println(loginfo.getEmail() + "/" + cartOwner.getEmail());

        if (StringUtils.equals(loginfo.getEmail(), cartOwner.getEmail())) {
            return true;
        } else {
            return false;
        }
    }
    // 장바구니 목록 조회 페이지 수량 변경 끝

    // 장바구니 목록 조회 페이지에서 상품 삭제 시작
    public void deleteCartProduct(Long cartProductId){
        CartProduct cartProduct = cpr.findById(cartProductId).orElseThrow(EntityNotFoundException::new);

        cpr.delete(cartProduct);
    }
    // 장바구니 목록 조회 페이지에서 상품 삭제 끝
}
