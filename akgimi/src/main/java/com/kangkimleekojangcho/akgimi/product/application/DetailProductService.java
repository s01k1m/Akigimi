package com.kangkimleekojangcho.akgimi.product.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.product.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.product.application.request.ProductDetailServiceRequest;
import com.kangkimleekojangcho.akgimi.product.application.response.ProductDetailServiceResponse;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class DetailProductService {
    private final QueryProductDbPort queryProductDbPort;
    public ProductDetailServiceResponse detail(ProductDetailServiceRequest serviceRequest) {
        Product product = queryProductDbPort.findById(serviceRequest.getId())
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_PRODUCT));
        return ProductDetailServiceResponse.builder()
                .price(product.getPrice())
                .image(product.getImage())
                .name(product.getName())
                .build();
    }
}
