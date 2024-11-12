package com.shopping.view;

import com.shopping.constant.Category;
import com.shopping.constant.ProductStatus;
import com.shopping.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

// 상품 등록이나 수정시 데이터를 전달해주는 view 클래스입니다.
// Spring에서는 이를 command 객체라고 부릅니다.
@Getter @Setter
public class ProductView {
    private Long id ;

    @NotBlank(message = "상품 이름은(는) 필수 입력 사항입니다.")
    private String name ;

    @NotNull(message = "가격은(는) 필수 입력 사항입니다.")
    private Integer price;

    @NotNull(message = "재고 수량은(는) 필수 입력 사항입니다.")
    private Integer stock ;


    @NotBlank(message = "상품 설명은(는) 필수 입력 사항입니다.")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    private Category category;

    // 데이터 베이스에 `이미지 경로 + 파일 이름` 형식으로 저장이 됩니다.
    private String image01;
    private String image02;
    private String image03;

    // 전송될 이미지 객체
    @NotNull(message = "대표 이미지는 필수 입력 사항입니다.")
    private MultipartFile img01 ;
    private MultipartFile img02 ;
    private MultipartFile img03 ;

    public void setImg01(@NotNull(message = "대표 이미지는 필수 입력 사항입니다.") MultipartFile img01) {
        this.image01 = this.getNewName(img01);
        this.img01 = img01;
    }

    private String getNewName(MultipartFile image) {
        // 선태한 이미지 객체와 UUID를 사용하여 새로운 이미지 이름을 반환합니다.
        UUID uuid = UUID.randomUUID();
        try {
            String originalName = image.getOriginalFilename();
            System.out.println("originalName : [" + originalName+ "]");
            if(originalName != null || !originalName.equals("")){
                String extension = originalName.substring(originalName.lastIndexOf(".")); // 파일의 확장자
                return uuid.toString() + extension ;
            }else{
                return null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null ;
        }
    }

    public void setImg02(MultipartFile img02) {
        this.image02 = this.getNewName(img02);
        this.img02 = img02;
    }

    public void setImg03(MultipartFile img03) {
        this.image03 = this.getNewName(img03);
        this.img03 = img03;
    }

    private String createBy;
    private LocalDateTime regDate ;

    // Model Mapper 객체 생성
    private static ModelMapper modelMapper = new ModelMapper();

    public Product createProductByModelMapper(){
        // Form 화면에서 넘어온 데이터(ProductView)를 Entity(Product)로 내가 맵핑 해줄께
        return modelMapper.map(this, Product.class); // this(읽고) > Product.class(쓸게)
    }

    public static ProductView of(Product product){
        // Entity(Product)의 정보를 Form 화면 데이터 형식으로 맵핑 해줄께
        return modelMapper.map(product, ProductView.class);
    }
    
    // 상품 등록시 추가했던 삭제될 이미지 정보(`이미지 경로 + 파일 이름`)
    private String oldImage01;
    private String oldImage02;
    private String oldImage03;
    
}
