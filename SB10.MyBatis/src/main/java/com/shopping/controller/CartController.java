package com.shopping.controller;


import com.shopping.service.CartService;
import com.shopping.view.CartDetailView;
import com.shopping.view.CartOrderView;
import com.shopping.view.CartProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    // 장바구니에 담기 시작
    private final CartService cs ;

    @PostMapping(value = "/cart") // prDetail > /cart 동일하게
    public @ResponseBody ResponseEntity addCart(@RequestBody @Valid CartProductView cpv, BindingResult br, Principal principal){
        if(br.hasErrors()){ // hasErrors = 오류가 있다 없다.
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = br.getFieldErrors();
            for (FieldError err:fieldErrors){
                sb.append(err.getDefaultMessage());
            }
            return  new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);

        }
        try{
            // principal : 시스템을 사용하고자 하는 인증된 사용자의 정보가 들어 있습니다.
            // SecurityConfig.java 파일과 관련이 있습니다.
            String email = principal.getName();
            Long cartProductId = cs.addCart(cpv,email);
            return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);

        }catch (Exception err){
            err.printStackTrace();
            return  new ResponseEntity<String>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 장바구니에 담기 끝

    // 장바구니 목록 조회하기 시작
    // 메인 화면에서 `장바구니` 링크를 클릭하였습니다.
    @GetMapping(value = "/cart") // declaration in header.html file
    public String cartHistory(Model model,Principal principal){
        String email = principal.getName();
        List<CartDetailView> cdvList = cs.getCartList(email);
        model.addAttribute("cartProducts",cdvList);

        return "/cart/cartList";
    }
    // 장바구니 목록 조회하기 끝

    // 장바구니 목록 조회 화면에서 수량 변경 시작
    // @PatchMapping은 HTTP PATCH 메소드 요청을 처리하기 위하여 사용되는데, 일반적으로 전체 보다는 부분적 업데이트를 수행하고자 할때 많이 사용합니다.
    @PatchMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity updateCartProduct(@PathVariable("cartProductId") Long cartProductId, int count , Principal principal){ // 카트 목록 페이지에서 특정 상품의 수량을 변경하였습니다.
        System.out.println("상품 번호 : " + cartProductId);
        System.out.println("변경할 수량 : " + count);
        String message ;

        if(count <= 0){
            message = "수량은 최소 1개 이상이어야 합니다.";
            return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName() ;

        if(cs.validateCartProduct(cartProductId, email) == false){
            message = "수정 권한이 없습니다.";
            return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
        }

        cs.updateCartProductCount(cartProductId, count); // 장바구니의 수량 변경하기
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);

    }
    // 장바구니 목록 조회 화면에서 수량 변경 끝

    // 장바구니 목록 조회 화면에서 상품 삭제 시작
    @DeleteMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity deleteCartProduct(@PathVariable("cartProductId") Long cartProductId, Principal principal){
        String email = principal.getName();

        if(!cs.validateCartProduct(cartProductId, email)){
            String message = "삭제 권한이 없습니다.";
            return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);

        }else {
            cs.deleteCartProduct(cartProductId);
            return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
        }
    }
    // 장바구니 목록 조회 화면에서 상품 삭제 끝

    // 장바구니 품목들 중 몇 개 주문 하기 시작
    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartProduct(@RequestBody CartOrderView cov, Principal principal) {
        List<CartOrderView> cartOrderViewList = cov.getCartOrderViewList();

        String message ;

        if(cartOrderViewList==null || cartOrderViewList.size()==0){
            message = "주문할 상품을 선택해 주세요.";
            return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST) ;
        }

        String email = principal.getName() ;

        for(CartOrderView bean : cartOrderViewList ){
            boolean bool = cs.validateCartProduct(bean.getCartProductId(), email);
            if(bool == false){
                message = "주문 권한이 없습니다.";
                return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST) ;
            }
        }

        // 주문 로직을 호출하고, 주문 아이디를 반환 받습니다.
        Long orderId = cs.orderCartProduct(cartOrderViewList, email);

        // 생성된 주문 번호와 함께 HTTP 응답 코드를 반환해 줍니다.
        return new ResponseEntity<Long>(orderId, HttpStatus.OK) ;

    }

    // 장바구니 품목들 중 몇 개 주문 하기 끝
}













