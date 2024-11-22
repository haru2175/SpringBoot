package com.shopping.service;

import com.shopping.view.ProductView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service // 이미지에 대한 업로드(복사)/삭제 등을 위한 유틸리티 클래스입니다.
public class FileService {
    @Value("${productImageLocation}") // c:/shop/image
    private String productImageLocation ; // 상품 이미지가 업로드 되는 경로

    public void uploadFile(ProductView pv) {
        // 3개의 이미지를 각각 업로드 합니다.
        uploadFileImage(pv.getImage01(), pv.getImg01());
        uploadFileImage(pv.getImage02(), pv.getImg02());
        uploadFileImage(pv.getImage03(), pv.getImg03());
    }

    private void uploadFileImage(String imageName, MultipartFile uploadFile) {
        FileOutputStream fos = null ;
        try{
            if(imageName == null){return ;}
            String fileUploadUrl = productImageLocation + "/" + imageName ;
            fos = new FileOutputStream(fileUploadUrl) ;
            byte[] fileData = uploadFile.getBytes() ;
            fos.write(fileData);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(fos != null){fos.close();}
            }catch(Exception ex2){
                ex2.printStackTrace();
            }
        }
    }

    // 상품 수정 화면에서 넘어온 이전 이미지 정보를 삭제합니다.
    public void deleteFile(ProductView pv) throws Exception{
        deleteFile(pv.getOldImage01());
        deleteFile(pv.getOldImage02());
        deleteFile(pv.getOldImage03());
    }

    private void deleteFile(String filePath) throws Exception{
        if(filePath==null){ // 대표 이미지가 아니면 null일 수 있습니다.
            System.out.println("삭제할 이미지 정보가 없습니다.");
            return;
        }

        String[] filePathList = filePath.split("/") ;
        String newFilePath = productImageLocation + "/" + filePathList[filePathList.length - 1];

        System.out.println("삭제될 파일 이름 : ");
        System.out.println(newFilePath);

        File deleteFile = new File(newFilePath);
        if(deleteFile.exists()){
            deleteFile.delete();
            System.out.println("해당 파일을 삭제하였습니다.");
        }else{
            System.out.println("해당 파일이 존재하지 않습니다.");
        }
    }

}
