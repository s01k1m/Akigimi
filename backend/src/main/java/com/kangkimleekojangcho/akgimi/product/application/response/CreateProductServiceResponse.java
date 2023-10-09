package com.kangkimleekojangcho.akgimi.product.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateProductServiceResponse {

    private final boolean isProductRegistered;
}
