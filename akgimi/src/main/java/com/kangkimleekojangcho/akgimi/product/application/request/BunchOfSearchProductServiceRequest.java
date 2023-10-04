package com.kangkimleekojangcho.akgimi.product.application.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BunchOfSearchProductServiceRequest {
    private final String name;
    private final Integer startMoney;
    private final Integer endMoney;
}
