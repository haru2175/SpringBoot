package com.shopping.mycartest;

import lombok.Setter;
import lombok.ToString;

@ToString @Setter
public class Sonata03 implements Car03 {
    private String name ;
    private int price ;
    private String maker ;

    public Sonata03(String name, int price, String maker) {
        this.name = name;
        this.price = price;
        this.maker = maker;
    }
}
