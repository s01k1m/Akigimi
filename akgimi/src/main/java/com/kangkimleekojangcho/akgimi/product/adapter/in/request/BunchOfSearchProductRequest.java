package com.kangkimleekojangcho.akgimi.product.adapter.in.request;

import com.kangkimleekojangcho.akgimi.product.application.request.BunchOfSearchProductServiceRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BunchOfSearchProductRequest {
    private final String name;
    private final Integer startMoney;
    private final Integer endMoney;

    public BunchOfSearchProductServiceRequest toServiceRequest() {
        return BunchOfSearchProductServiceRequest.builder()
                .name(this.name)
                .startMoney(this.startMoney)
                .endMoney(this.endMoney)
                .build();
    }
}
