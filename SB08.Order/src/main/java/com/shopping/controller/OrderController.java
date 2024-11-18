package com.shopping.controller;

import com.shopping.service.OrderService;
import com.shopping.view.OrderHistoryView;
import com.shopping.view.OrderView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

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

    // 과거 주문 내역 조회하기 시작
    @Value("${pageSize}") // beans 임포트
    private int pageSize;

    @Value("${pageCount}") // beans 임포트
    private int pageCount;

    @GetMapping(value = {"/orders","/orders{page}"})
    public String orderHistory(@PathVariable("page") Optional<Integer> page, Model model,Principal principal){
        // isPresent = Null 체크
        int pageNumber = page.isPresent() ? page.get() : 0 ;

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        String email = principal.getName(); // principal = SecurityConfig 연계되어있음.

        // 변수 orders에 해당 고객이 이전에 주문했던 내역 정보를 저장합니다.
        Page<OrderHistoryView> orders = os.getOrderList(email,pageable);

        model.addAttribute("orders",orders);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("page",pageable.getPageNumber());

        return "order/orderHistory";
    }


    // 과거 주문 내역 조회하기 끝

    // 주문 내역 취소하기 시작
    @PostMapping(value = "/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal){
        String email = principal.getName();

        if(os.validateOrder(orderId,email)){
            os.cancelOrder(orderId);
            return new ResponseEntity<Long>(orderId,HttpStatus.OK) ;

        }else {
            String message = "주문 취소 권한이 없습니다." ;
            return new ResponseEntity<String>(message,HttpStatus.FORBIDDEN) ;
        }
    }
    // 주문 내역 취소하기 끝
}
