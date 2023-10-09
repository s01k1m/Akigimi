package com.kangkimleekojangcho.akgimi.product.adapter.in;


import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.product.adapter.in.request.BunchOfSearchProductRequest;
import com.kangkimleekojangcho.akgimi.product.adapter.in.request.CreateProductRequest;
import com.kangkimleekojangcho.akgimi.product.adapter.in.request.ProductDetailRequest;
import com.kangkimleekojangcho.akgimi.product.application.DetailProductService;
import com.kangkimleekojangcho.akgimi.product.application.SaveProductService;
import com.kangkimleekojangcho.akgimi.product.application.SearchProductService;
import com.kangkimleekojangcho.akgimi.product.application.response.BunchOfSearchProductServiceResponse;
import com.kangkimleekojangcho.akgimi.product.application.response.CreateProductServiceResponse;
import com.kangkimleekojangcho.akgimi.product.application.response.ProductDetailServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final SaveProductService saveProductService;
    private final SearchProductService searchProductService;
    private final DetailProductService detailProductService;

    // 물건 추가
    @PostMapping("/product/new")
    public ResponseEntity<SuccessResponse<CreateProductServiceResponse>> createProduct(
            CreateProductRequest request,
            HttpServletRequest servletRequest
    ){
        long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        if(request.getThumbnail().size()!=1 || request.getImage().size()!=1){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "한 개의 이미지를 업로드해야 합니다.");
        }
        CreateProductServiceResponse createProductServiceResponse = saveProductService.save(userId,request.toServiceRequest());
        return ResponseFactory.success(createProductServiceResponse);
    }


    // 물건 검색
    @GetMapping("/product/search")
    public ResponseEntity<SuccessResponse<BunchOfSearchProductServiceResponse>> searchProduct(
            BunchOfSearchProductRequest bunchOfSearchProductRequest
    ){
        return ResponseFactory.success(
                searchProductService.search(bunchOfSearchProductRequest.toServiceRequest())
        );
    }

    // 물건 상세정보
    @GetMapping("/product/detail")
    public ResponseEntity<SuccessResponse<ProductDetailServiceResponse>> detailProduct(
        ProductDetailRequest productDetailRequest
    ){
        return ResponseFactory.success(
                detailProductService.detail(productDetailRequest.toServiceRequest())
        );
    }

}