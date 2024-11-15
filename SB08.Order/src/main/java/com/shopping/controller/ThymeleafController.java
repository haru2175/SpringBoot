package com.shopping.controller;

import com.shopping.common.GenerateData;
import com.shopping.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf/")
public class ThymeleafController {
    // http://localhost:8989/thymeleaf/ex01 요청이 들어 오면
    @GetMapping(value = "ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data", "타임 리트 1번 예시입니다.");
        return "thymeleafEx/viewEx01" ; // 해당 페이지로 이동하겠습니다.
    }

    @GetMapping(value = "ex02")
    public String thymeleafExample02(Model model){
        Product bean = GenerateData.getProductOne() ;
        model.addAttribute("bean", bean);
        return "thymeleafEx/viewEx02" ;
    }

    @GetMapping(value = "ex03")
    public String thymeleafExample03(Model model){
        List<Product> beanList = GenerateData.getProductList(10) ;
        model.addAttribute("beanList", beanList);
        System.out.println("목록 개수 : " + beanList.size());
        return "thymeleafEx/viewEx03" ;
    }

    @GetMapping(value = "ex04")
    public String thymeleafExample04(Model model){
        List<Product> beanList = GenerateData.getProductList(10) ;
        model.addAttribute("beanList", beanList);
        return "thymeleafEx/viewEx04" ;
    }

    @GetMapping(value = "ex05")
    public String thymeleafExample05(Model model){
        List<Product> beanList = GenerateData.getProductList(10) ;
        model.addAttribute("beanList", beanList);

        return "thymeleafEx/viewEx05" ;
    }

    @GetMapping(value = "ex06")
    public String thymeleafExample06(){
        return "thymeleafEx/viewEx06" ;
    }

    @GetMapping(value = "ex07")
    public String thymeleafExample07(String param1, String param2, Model model){
        if(param1 == null){param1 = "호호호";}
        if(param2 == null){param2 = "크크크";}

        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleafEx/viewEx07" ;
    }

    @GetMapping(value = "layout")
    public String thymeleafExampleLayout(){
        return "thymeleafEx/pageLayout" ;
    }
}
