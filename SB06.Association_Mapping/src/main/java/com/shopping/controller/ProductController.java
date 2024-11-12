package com.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping(value = "/admin/product/insert")
    public String doGetInsert(){
        return "product/prInsertForm" ;
    }
}
