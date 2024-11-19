package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CafeItem {
    private Long id;
    private String name ;
    private Integer price;
    private String category;
    private Integer stock;
    private String image;
    private String description;
    private String sellStatus;

    public CafeItem() {
    }

    public CafeItem(Long id, String name, Integer price, String category, Integer stock, String image, String description, String sellStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.image = image;
        this.description = description;
        this.sellStatus = sellStatus;
    }
}
