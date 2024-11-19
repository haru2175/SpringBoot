package com.shopping.service;

import com.shopping.entity.Product;
import com.shopping.repository.ProductRepository;
import com.shopping.view.MainView;
import com.shopping.view.ProductView;
import com.shopping.view.SearchView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository pr ; // DB 작업 대행자
    private final FileService fs ; // 업로드 대행자

    // 상품 등록 화면에서 넘겨진  ProductView 객체를 사용하여 상품을 등록해주는 서비스 메소드입니다.
    public Long saveProduct(ProductView pv){
        // 등록 화면에서 입력한 내용을 Entity으로 변경합니다.
        Product product = pv.createProductByModelMapper();

        String imagePath = "/images/image/" ;
        // 1번 이미지는 대표 이미지로써 반드시 입력이 되어야 합니다.
        product.setImage01(imagePath + product.getImage01());

        // 2번과 3번 이미지는 입력이 되지 않을 수도 있습니다.
        if(product.getImage02() != null){
            product.setImage02(imagePath + product.getImage02());
        }

        if(product.getImage03() != null){
            product.setImage03(imagePath + product.getImage03());
        }

        pr.save(product) ; // 데이터 베이스에 저장
        fs.uploadFile(pv); // 업로드 요청

        return product.getId() ;
    }

    // 이미 등록된 상품의 id 정보를 이용하여 수정하고자 하는 데이터를 읽어 들입니다.
    public ProductView getProductDetail(Long productId){
        Product product = pr.findById(productId).orElseThrow(EntityNotFoundException::new);

        ProductView pv = ProductView.of(product);
        return pv ;
    }

    // 화면 영역에서 넘어온 command 객체 정보를 이용하여 다음 작업을 수행합니다.
    // 이 메소드는 Post 호출 방식에서 사용됩니다.
    public Long updateProduct(ProductView pv) throws Exception{
        Product product = pv.createProductByModelMapper();
        pr.save(product) ; // 데이터 베이스에 저장
        fs.uploadFile(pv); // 이미지 저장소에 파일 업로드
        fs.deleteFile(pv); // 상품 등록시 업로드한 이미지는 삭제

        // 보여지는 화면도 갱신이 되어야 하므로 이 메소드를 호출합니다.
        product.updateProduct(pv); // 보이는 화면과 데이터 베이스의 동기화

        return product.getId();
    }

    // 관리자를 위한 상품 목록 페이지
    public Page<Product> getAdminProductPage(SearchView sv, Pageable pageable){
        // 상품 필드 검색 조건 sv와 페이징 객체 pageable를 사용하여 페이징 객체 정보를 반환합니다.
        return pr.getAdminProductPage(sv, pageable) ;
    }

    // 메인 화면 : 일반인들이 접근 가능한 상품 목록 페이지
    public Page<MainView> getMainProductPage(SearchView sv, Pageable pageable){
        return pr.getMainProductPage(sv, pageable) ;
    }
}
