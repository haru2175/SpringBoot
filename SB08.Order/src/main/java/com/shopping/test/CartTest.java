package com.shopping.test;

import com.shopping.common.GenerateData;
import com.shopping.entity.Cart;
import com.shopping.entity.Member;
import com.shopping.repository.CartRepository;
import com.shopping.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
public class CartTest {
    @Autowired
    private MemberRepository mr ;

    @Autowired
    private CartRepository cr ;

    @Test
    @DisplayName("카트와 회원 매핑 테스트")
    public void findCartAndMemberTest(){
        Member member = GenerateData.createMember(); // 카트 소유자
        Member savedMember = mr.save(member);

        Cart cart = new Cart() ;
        cart.setMember(savedMember); // 카트의 소유자를 지정합니다.

        // 데이터 베이스에 인서트 합니다.
        cr.save(cart) ;

        // savedCart : 데이터 베이스에 저장된 카트의 정보
        Cart savedCart = cr.findById(cart.getId()).orElseThrow(EntityNotFoundException::new) ;

        System.out.println("회원 정보");
        System.out.println(member);

        System.out.println("\n카트 정보");
        System.out.println(savedCart);
    }
}
