package com.shopping.test;

import com.shopping.common.GenerateData;
import com.shopping.entity.CartProduct;
import com.shopping.entity.Member;
import com.shopping.entity.Product;
import com.shopping.repository.CartProductRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.service.CartService;
import com.shopping.view.CartProductView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
// @Transactional  // import org.springframework.transaction.annotation.Transactional;
//@Transactional 이걸쓰면 테스트하고 롤백된다.
public class CartServiceTest {
    // CartServiceTest01 장바구니에 담기 시작
    @Autowired
    private CartProductRepository cpr ;

    @Autowired
    private ProductRepository pr ;

    @Autowired
    private CartService cs;

    @Autowired
    private MemberRepository mr ;

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Product product = GenerateData.getProductOne();
        Member member = GenerateData.createMember() ;

        Member savedMember = mr.save(member);
        Product savedProduct = pr.save(product) ;

        CartProductView cpv = new CartProductView();

        Long productId = savedProduct.getId() ; // 담을 상품 아이디
        int count = 5 ; // 담기 수량

        cpv.setProductId(productId);
        cpv.setCount(count);

        String email = member.getEmail() ;
        Long cartProductId = cs.addCart(cpv, email);

        CartProduct cartProduct = cpr.findById(cartProductId).orElseThrow(EntityNotFoundException::new) ;

        System.out.println("구매자 : " + email);
        System.out.println("구매자 아이디 : " + savedMember.getId());
        System.out.println("상품 아이디 : " + product.getId());
        System.out.println("저장된 상품 아이디 : " + cartProduct.getProduct().getId());
        System.out.println("상품 개수 : " + cpv.getCount());
        System.out.println("저장된 상품 개수 : " + cartProduct.getCount());

        /*
select c.cart_id, c.member_id, cp.count, cp.product_id
from carts c inner join cart_products cp
on c.cart_id = cp.cart_id ;
        */
    }
    // CartServiceTest01 장바구니에 담기 끝
}
