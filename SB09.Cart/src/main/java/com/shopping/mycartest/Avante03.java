package com.shopping.mycartest;

import lombok.Setter;
import lombok.ToString;

@ToString @Setter
public class Avante03 implements Car03 {
    private String name ;
    private int price ;
    private String comment ;

    public Avante03(String name, int price, String comment) {
        this.name = name;
        this.price = price;
        this.comment = comment;
    }
}
