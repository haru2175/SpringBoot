package com.shopping.common;

import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import com.shopping.entity.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateData {

    public static Product getProductOne(){
        // 샘플용 상품 1개를 반환해 줍니다.
        return createProduct(true,0);
    }

    public static List<Product> getProductList(int size){
        List<Product> list = new ArrayList<Product>();
        for (int i = 0; i < size; i++) {
            list.add(createProduct(true,i));
        }
        return list;
    }

    private static String getProductName(){
        String[] fruits = {"사과", "배", "오렌지", "포도", "키위", "바나나"};
        return fruits[new Random().nextInt(fruits.length)] ;
    }

    private static String getDescriptionData(String name){
        String[] description = {"달아요.", "맛있어요.", "맛없어요.", "떫어요.", "새콤해요.", "상큼해요."};
        return name + "는(은) " + description[new Random().nextInt(description.length)] ;
    }

    private static int getRandomDataRange(int start, int end){
        // start <= somedata <= end
        return new Random().nextInt(end) + start ;
    }

    public static Product createProduct(boolean isSell, int index) { // make Product bean object
        Product product = new Product();

        if(isSell){
            product.setProductStatus(ProductStatus.SELL);
        }else{
            product.setProductStatus(ProductStatus.SOLD_OUT);
        }
        switch (index%3){
            case 0:
                product.setCategory(Category.BREAD);
                break;
            case 1:
                product.setCategory(Category.BEVERAGE);
                break;
            case 2:
                product.setCategory(Category.FRUIT);
                break;
        }

        String[] ids = {"hong", "kim", "choi", "lee", "sim", "park"};

        //product.setId();
        String productName = getProductName();
        product.setName(productName);
        product.setCreatedBy(ids[new Random().nextInt(ids.length)]);
        String description = getDescriptionData(productName);
        product.setDescription(description);
        product.setImage01("abcd.jpg"); // 필수 입력 사항입니다.
        product.setPrice(1000 * getRandomDataRange(1, 10));
        product.setStock(111 * getRandomDataRange(1, 9));
        product.setRegDate(LocalDateTime.now());
        return product ;
    }
}
