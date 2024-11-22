package com.shopping.controller;

import com.shopping.common.GenerateItemData;
import com.shopping.dto.CafeItem;
import com.shopping.dto.StudentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController // 어노테이션을 작성해 두면, 스프링 컨테이너가 스스로 객체를 생성하여 컨트롤러를 만들어 줍니다.
@RequestMapping(value = "react/data/")
public class ForReactController {

    // 1페이지에 3개씩 보여주는 페이징 처리를 해주세요.
    @GetMapping(value = "/exam07/{pageNumber}")
    public List<CafeItem> getData07(@PathVariable(value = "pageNumber") int pageNumber){
        System.out.println(this.getClass() + " getData07() method called");
        List<CafeItem> beanList = generator.createCafeItemMany();
        int pageSize = 3 ;
        int beginRow = (pageNumber-1) * pageSize + 1 ;
        int endRow = pageNumber * pageSize ;
        int totalCount = this.getData07();
        if(endRow > totalCount){endRow = totalCount ; }

        return beanList.subList(beginRow-1, endRow) ;
    }

    @GetMapping(value = "/exam07")
    public int getData07(){
        System.out.println(this.getClass() + " getData07() method called");
        List<CafeItem> beanList = generator.createCafeItemMany();
        return beanList.size() ;
    }

    @GetMapping(value = "exam06/{pageNumber}")
    public List<StudentDto> getData06(@PathVariable(value = "pageNumber") int pageNumber){
        System.out.println(this.getClass() + " getData06() method called");
        List<StudentDto> beanList = new ArrayList<StudentDto>();
        int totalCount = 45 ; // 전체 데이터 개수
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("000");

        for (int i = 0; i < totalCount; i++) {
            String imsi = df.format((i+1)) ;
            beanList.add(new StudentDto("id"+imsi, "data"+imsi, rand.nextInt(70)+30, rand.nextInt(70)+30, rand.nextInt(70)+30)) ;
        }
        int pageSize = 10 ;
        int beginRow = (pageNumber-1) * pageSize + 1 ;
        int endRow = pageNumber * pageSize ;
        if(endRow > totalCount){endRow = totalCount ; }

        return beanList.subList(beginRow-1, endRow) ;
    }



    @GetMapping(value = "exam05")
    public List<StudentDto> getData05(){
        System.out.println(this.getClass() + " getData05() method called");

        List<StudentDto> beanList = new ArrayList<StudentDto>();

        String[] ids = {"lee", "son", "kang", "choi", "yoon", "kim"};
        String[] names = {"이주현", "손병훈", "강호사", "최경숙", "윤미현", "김준혁"};
        int[][] jumsu = {{75, 60, 80}, {80, 95, 70}, {65, 90, 50}, {55, 80, 80}, {50, 60, 70}, {80, 90, 60}};

        for (int i = 0; i < names.length ; i++) {
            StudentDto bean = new StudentDto(ids[i], names[i], jumsu[i][0], jumsu[i][1], jumsu[i][2]);
            beanList.add(bean);
        }
        return beanList ;
    }

    @GetMapping(value = "/exam06") // 시험 응시자 수를 반환합니다.
    public int getData06(){
        System.out.println(this.getClass() + " getData06() method called");
        List<StudentDto> beanList = generator.getStudentListMany();
        return beanList.size() ;
    }

    // http://localhost:8989/react/data/exam04/빵
    // http://localhost:8989/react/data/exam04/케익
    // http://localhost:8989/react/data/exam04/호호
    @GetMapping(value = "exam04/{category}")
    public List<CafeItem> getData04(@PathVariable(value = "category", required = false) String category){
        // String category = request.getParameter("category") ;
        System.out.println(this.getClass() + " getData04() method called");

        // beanList는 전체 품목을 의미합니다.
        List<CafeItem> beanList = generator.createCafeItemMany() ;
        if(category==null){category=""; }

        final String finalCategory = category;

        // 자바 stream API 공부 좀 해야 합니다.
        List<CafeItem> filterItems = beanList.stream()
                .filter(item -> item.getCategory().equals(finalCategory))
                .collect(Collectors.toList());

        // 목록이 0개이면 전체 목록을 반환합니다.
        if(filterItems.size() == 0){filterItems = beanList ;}

        return filterItems ;
    }

    @GetMapping(value = "exam04") // 모든 상품에 대하여 카테고리 목록을 집합 형식으로 반환합니다.
    public Set<String> getData04(){
        System.out.println(this.getClass() + " getData04() method called");
        List<CafeItem> beanList = generator.createCafeItemMany() ;
        Set<String> itemSet = new HashSet<String>();

        for(CafeItem item:beanList){
            itemSet.add(item.getCategory()) ;
        }
        return itemSet ;
    }

    @GetMapping(value = "exam03")
    public List<StudentDto> getData03(){
        System.out.println(this.getClass() + " getData03() method called");

        List<StudentDto> beanList = new ArrayList<StudentDto>();

        String[] ids = {"lee", "kim", "son"};
        String[] names = {"이정선", "김경선", "손흥식"};
        int[][] jumsu = {{10, 60, 80}, {30, 40, 70}, {20, 90, 50}};

        for (int i = 0; i < names.length ; i++) {
            StudentDto bean = new StudentDto(ids[i], names[i], jumsu[i][0], jumsu[i][1], jumsu[i][2]);
            beanList.add(bean);
        }

        return beanList ;
    }


    // http://localhost:8989/react/data/exam02
    @GetMapping(value = "exam02")
    public List<CafeItem> getData02(){
        List<CafeItem> beanList = generator.createCafeItemMany();
        System.out.println(this.getClass() + " getData02() method called");
        return beanList ;
    }


    // http://localhost:8989/react/data/exam01
    @GetMapping(value = "exam01")
    public CafeItem getData01(){
        CafeItem bean = generator.getOneCafeItem();
        System.out.println(this.getClass() + " getData01() method called");
        return bean ;
    }

    GenerateItemData generator = null ;

    public ForReactController() {
        generator = new GenerateItemData();
    }
}
