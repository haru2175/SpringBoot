package com.shopping.common;

import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import com.shopping.entity.Member;
import com.shopping.entity.Product;
import com.shopping.view.MemberView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateData {

    public static Product getProduct() {
        //샘플반환
        return createProduct(true, 0);
    }

    public static List<Product> getProductList(int size) {
        //샘플반환
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            productList.add(createProduct(true, i));
        }
        return productList;
    }

    // 회원 1명에 대한 샘플용 데이터를 만들어 주는 메소드입니다.
    public static Member createMember(){
        String[] name_list = {"홍민호", "임만호", "김유정", "부성업", "강유찬", "문호식"};
        String[] email_list = {"bluesky", "peace", "love", "hello", "world", "oecan"};
        String[] address_list = {"마포구 합정동", "용산구 용문동", "서대문구 홍제동", "은평구 응암동", "동대문구 제기동", "금천구 가산동"};
        MemberView mv = new MemberView() ;

        String email = email_list[new Random().nextInt(email_list.length)] + getRandomDataRange(1, 10000);
        email += "@example.com";
        mv.setEmail(email);

        String name = name_list[new Random().nextInt(name_list.length)] + getRandomDataRange(1, 10000);
        mv.setName(name);

        String address = address_list[new Random().nextInt(address_list.length)] + " " + getRandomDataRange(1, 100) + "번지";
        mv.setAddress(address);
        mv.setPassword("abcd1234");
        return Member.createMember(mv, new BCryptPasswordEncoder());
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

    
    // 랜덤 값 입력받아서 임시 상품 더미 데이터를 자동으로 생성
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
