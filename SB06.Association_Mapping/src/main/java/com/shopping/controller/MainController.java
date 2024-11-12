package com.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    // /가 입력이 되면 메인 페이지로 이동합니다.
    @RequestMapping(value = "/")
    public String main(){
        return "main" ;
    }
}
