package com.shopping.controller;

import com.shopping.entity.Product;
import com.shopping.service.ProductService;
import com.shopping.view.ProductView;
import com.shopping.view.SearchView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {
//    @GetMapping(value = "/admin/product/insert")
//    public String doGetInsert(){
//        return "product/prInsertForm" ;
//
//    }


    @GetMapping(value = "/admin/product/insert")
    public String doGetInsert(Model model){
        model.addAttribute("productView",new ProductView());
        return "product/prInsertForm" ;

    }

    private final ProductService ps;

    @PostMapping(value = "/admin/product/insert")
    public String doPostInsert(@Valid ProductView pv, BindingResult br, Model model,
     @RequestParam("img01")MultipartFile img01){
        // pv : 사용자가 기입한 정보를 담고 있는 command 객체입니다.

        if(br.hasErrors()){ // 유효성 검사에 문제가 있음
            return "/product/prInsertForm" ;
        }

        // isEmpty() : 사용자가 이미지 파일을 선택하지 않았거나, 전송된 파일이 비어 있을 때 참입니다.
        if(img01.isEmpty() && pv.getId() == null){
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수 입력 값입니다.");
            return "/product/prInsertForm" ;
        }

        try{
            // 서비스 객체에게 command 객체의 저장을 부탁합니다.
            ps.saveProduct(pv);

        }catch(Exception ex){
            ex.printStackTrace();
            model.addAttribute("errorMessage", "예외가 발생하였습니다.");
            return "/product/prInsertForm" ;
        }

        return "redirect:/" ; // 메인 페이지로 이동

    }

    // 상품 수정 페이지로 이동하기 위한 코드를 작성합니다.
    @GetMapping(value = "/admin/product/update/{productId}")
    public String doGetUpdate(@PathVariable("productId") Long productId, Model model){
        ProductView pv = ps.getProductDetail(productId);

        if(pv == null){
            String message = "상품 번호 " + productId + "는(은) 존재하지 않습니다.";
            System.out.println(message);
            model.addAttribute("errorMessage",message);
            return "redirect:/" ; // go to main page

        }else {
            model.addAttribute("productView",pv);
            return "product/prUpdateForm" ;
        }
    }


    // 상품 수정 페이지에서 '수정' 버튼을 클릭합니다.
    @PostMapping(value = "/admin/product/update")
    public String doPostUpdate(@Valid ProductView pv, BindingResult br, Model model,
                               @RequestParam("img01")MultipartFile img01){
        final String whenError = "/product/prUpdateForm";

        // pv : 사용자가 수정한 정보를 담고 있는 command 객체입니다.
        if(br.hasErrors()){ // 유효성 검사에 문제가 있음
            return whenError ;
        }

        // isEmpty() : 사용자가 이미지 파일을 선택하지 않았거나, 전송된 파일이 비어 있을 때 참입니다.
        if(img01.isEmpty() && pv.getImg01() == null){
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수 입력 값입니다.");
            return whenError ;
        }

        try{
            // 서비스 객체에게 command 객체의 저장을 부탁합니다.
            ps.updateProduct(pv);
            return "redirect:/" ; // 메인 페이지로 이동

        }catch(Exception ex){
            ex.printStackTrace();
            model.addAttribute("errorMessage", "상품 수정에 문제가 발생하였습니다.");
            return whenError ;
        }
    }

    // 상품 목록 관리자 페이지 시작
    // import org.springframework.beans.factory.annotation.Value;
    @Value("${pageSize}")
    private int pageSize ; // 1개의 페이지에 보여주는 행 갯수

    @Value("${pageCount}")
    private int pageCount ; // 하단에 보여지는 최대 페이지 번호 개수

    // import java.util.Optional;
    // 상품 목록 관리자 페이지를 위한 컨트롤러 메소드입니다.
    // 2가지 이상의 요청에 응대하려면 중괄호 {}를 사용하면 됩니다.
    // Optional 클래스는 자바의 NPE 문제를 해결하기 위하여 제공해주는 클래스입니다.
    // NPE : Null Pointer Exception
    @GetMapping(value = {"/admin/product/list","/admin/product/list/{page}"})
    public String productList(SearchView sv, @PathVariable("page") Optional<Integer> page,Model model){
        System.out.println("SearchView Information : " + sv);

        // isPresent => 값이 들어있는지 없는지 확인하는것.
        Integer pageNumber = page.isPresent() ? page.get() : 0;
        System.out.println("Current Page Number : " + pageNumber);

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Product> products = ps.getAdminProductPage(sv, pageable);
        long totalCount = products.getTotalElements(); // getTotalElements > 페이징클래스
        System.out.println("Total Data Count : " + totalCount);

        model.addAttribute("products",products); // html 문서에서 보여줄 목록
        model.addAttribute("pageCount",pageCount); // 하단에 보여지는 최대 페이지 번호 개수
        model.addAttribute("totalCount","총" + totalCount + "건"); // 전체 데이터 개수
        model.addAttribute("searchView",sv); // 필드 검색 조건

        return "product/prList";
    }

    // 상품 목록 관리자 페이지 끝

}
