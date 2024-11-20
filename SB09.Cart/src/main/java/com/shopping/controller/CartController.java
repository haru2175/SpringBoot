package com.shopping.controller;


import com.shopping.service.CartService;
import com.shopping.view.CartProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
