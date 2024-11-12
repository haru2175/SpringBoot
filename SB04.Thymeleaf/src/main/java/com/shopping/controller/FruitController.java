package com.shopping.controller;

import com.shopping.entity.Fruit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FruitController {
    // http://localhost:8989/fruit
    @GetMapping("/fruit")
    public Fruit test01(){
        Fruit bean = new Fruit();
        bean.setId("banana");
        bean.setName("바나나");
        bean.setPrice(1000);
        return bean;
    }

    @GetMapping("/fruit/list")
    public List<Fruit> test02(){
        List<Fruit> fruitList = new ArrayList<Fruit>();
        fruitList.add(new Fruit("apple", "사과", 1000));
        fruitList.add(new Fruit("pear", "나주배", 2000));
        fruitList.add(new Fruit("grape", "포도", 3000));
        return fruitList;
    }
}
