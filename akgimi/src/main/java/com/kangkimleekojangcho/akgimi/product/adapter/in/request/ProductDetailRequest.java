package com.kangkimleekojangcho.akgimi.product.adapter.in.request;

import com.kangkimleekojangcho.akgimi.product.application.request.ProductDetailServiceRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailRequest {
    private final Long id;

    public ProductDetailServiceRequest toServiceRequest() {
        return ProductDetailServiceRequest.builder()
                .id(this.id)
                .build();
    }
}
