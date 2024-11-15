package com.shopping.controller;

import com.shopping.service.ProductService;
import com.shopping.view.MainView;
import com.shopping.view.SearchView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
//    // /가 입력이 되면 메인 페이지로 이동합니다.
//    @RequestMapping(value = "/")
//    public String main(){
//        return "main" ;
//    }

    @Value("${pageSize}")
    private int pageSize ;

    @Value("${pageCount}")
    private int pageCount ;

    private final ProductService ps ;

    // /가 입력이 되면 메인 페이지로 이동합니다.
    @RequestMapping(value = "/")
    public String main(SearchView sv, Optional<Integer> page, Model model){
        if(sv.getSearchKeyword() == null){
            sv.setSearchKeyword("");
        }
        System.out.println(this.getClass() + " SearchView Information : " + sv);

        Integer pageNumber = page.isPresent() ? page.get() : 0 ;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<MainView> products = ps.getMainProductPage(sv, pageable) ;

        model.addAttribute("products", products);
        model.addAttribute("searchView", sv);
        model.addAttribute("pageCount", pageCount);

        return "main" ;
    }
}
