package com.shopping.common;

import com.shopping.dto.CafeItem;
import com.shopping.dto.StudentDto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateItemData {
    public CafeItem getOneCafeItem(){
        return  new CafeItem(2L, "크로와상", 2000, "빵", 222, "croissant_02.png", "프랑스의 대표적인 베이커리 중 하나로, 층층이 쌓인 반죽에 버터를 추가하여 구워낸 과자입니다.", "판매");

    }

    public List<CafeItem> createCafeItemMany() {
        List<CafeItem> cafeItemList = new ArrayList<CafeItem>();

        cafeItemList.add(new CafeItem(1L, "프렌치 바게트", 1000, "빵", 111, "french_baguette_01.png", "프랑스의 대표적인 빵 중 하나로, 길쭉하고 얇은 형태의 식빵입니다. 바삭하면서도 촉촉한 식감과 진한 맛이 특징입니다.", "판매"));

        cafeItemList.add(new CafeItem(2L, "크로와상", 2000, "빵", 222, "croissant_02.png", "프랑스의 대표적인 베이커리 중 하나로, 층층이 쌓인 반죽에 버터를 추가하여 구워낸 과자입니다.", "판매"));

        cafeItemList.add(new CafeItem(3L, "아메리카노", 3000, "음료수", 333, "americano01.png", "에스프레소의 쓴 맛과 향을 좋아하는 사람들이 물을 추가해서 즐기는 음료로, 물과 에스프레소의 비율에 따라서 쓴 맛과 진하게 즐길 수 있습니다.", "판매"));

        cafeItemList.add(new CafeItem(4L, "카푸치노", 4000, "음료수", 444, "cappuccino01.png", "스팀밀크와 거품을 올린 것을 섞어 만든 이탈리아의 전통적인 커피 음료입니다.", "판매"));

        cafeItemList.add(new CafeItem(5L, "스폰지 케이크", 5000, "케익", 555, "sponge_cake_01.png", "가장 일반적인 케이크로, 부드럽고 공기가 많은 스폰지 텍스처를 가지고 있습니다. 일반적으로 크림, 과일, 초콜릿 등 다양한 토핑과 함께 제공됩니다.", "판매"));

        cafeItemList.add(new CafeItem(6L, "초콜릿 케이크", 6000, "케익", 666, "chocolate_cake_01.png", "초콜릿으로 만든 케이크로, 풍부하고 진한 초콜릿 맛을 가지고 있습니다. 초콜릿으로 만든 케이크 스폰지와 초콜릿으로 만든 크림 또는 가나슈를 사용하여 제작됩니다.", "품절"));

        cafeItemList.add(new CafeItem(7L, "레드 와인", 7000, "와인", 777, "redwine01.png", "레드 와인은 포도의 피부와 씨를 함께 발효시킨 후 얻어지는 와인으로, 일반적으로 빨간색 혹은 갈색의 색상을 가지고 있습니다.레드 와인은 탄닌과 풍부한 풍미를 가지며, 복잡하고 풍부한 향과 맛을 나타낼 수 있습니다.", "품절"));

        cafeItemList.add(new CafeItem(8L, "화이트 와인", 8000, "와인", 888, "whitewine01.png", "화이트 와인은 포도 주스를 포도 피부와 씨를 제거한 후 발효시킨 와인으로, 일반적으로 노란색, 노랑색 또는 초록빛이 납니다. 화이트 와인은 가벼우며 시원하고 깔끔한 맛과 향을 가지고 있습니다.", "품절"));

        return cafeItemList ;
    }

    public List<StudentDto> getStudentListMany(){
        List<StudentDto> beanList = new ArrayList<StudentDto>();
        Random rand = new Random() ;
        DecimalFormat df = new DecimalFormat("000");
        for (int i = 0; i < 256 ; i++) {
            String imsi = df.format((i+1));
            StudentDto bean = new StudentDto("id"+imsi, "data"+imsi, rand.nextInt(70)+30, rand.nextInt(70)+30, rand.nextInt(70)+30);
            beanList.add(bean);
        }
        return beanList ;
    }
}
