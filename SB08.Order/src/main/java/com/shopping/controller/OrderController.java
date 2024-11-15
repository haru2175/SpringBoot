package com.shopping.controller;

import com.shopping.service.OrderService;
import com.shopping.view.OrderView;
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
public class OrderController {
    // 주문 기능 구현하기 시작
    private final OrderService os ;

    // prDetail.html 문서에서 Ajax를 사용하여 Json 형식으로 데이터를 넘겨 줍니다.
    // @RequestBody : 클라이언트에서 넘어온 Json/Xml을 Java 객체로 변환해 줍니다.
    // ResponseEntity : Http 상태 코드, 헤더 및 응답에 대한 본문을 구성하기 위한 클래스입니다.
    // @ResponseBody : 컨트롤러가 반환하는 데이터를 JSON/XML 형식으로 직렬화시켜 HTTP 응답의 본문으로 전송해줍니다.

    @PostMapping(value="/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderView ov, BindingResult br, Principal principal){
        if(br.hasErrors()){
            StringBuilder sb = new StringBuilder() ;
            List<FieldError> fieldErrors = br.getFieldErrors();
            for(FieldError ferr : fieldErrors){
                sb.append(ferr.getDefaultMessage()) ;
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST) ;
        }

        // principal : 사용자 인증 정보를 가지고 있는 객체
        String email = principal.getName() ; // SecurityConfig.java 파일 참조
        Long orderId = 0L ;

        try{
            orderId = os.order(ov, email);
            System.out.println("주문 성공");
            return new ResponseEntity<Long>(orderId, HttpStatus.OK) ;

        }catch (Exception err){
            err.printStackTrace();
            return new ResponseEntity<String>(err.getMessage(), HttpStatus.BAD_REQUEST) ;
        }
    }

    // 주문 기능 구현하기 끝
}
