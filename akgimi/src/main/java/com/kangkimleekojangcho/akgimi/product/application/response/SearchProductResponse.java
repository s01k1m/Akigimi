package com.kangkimleekojangcho.akgimi.product.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchProductResponse {
    private final Long Id;
    private final String name;
    private final Integer price;
    private final String image;
}
