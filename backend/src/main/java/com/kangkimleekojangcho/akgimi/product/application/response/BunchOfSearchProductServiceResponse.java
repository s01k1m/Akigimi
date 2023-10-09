package com.kangkimleekojangcho.akgimi.product.application.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BunchOfSearchProductServiceResponse {
    private final List<SearchProductResponse> searchProductResponseList;
}
