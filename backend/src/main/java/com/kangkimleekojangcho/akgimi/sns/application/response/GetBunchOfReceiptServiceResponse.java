package com.kangkimleekojangcho.akgimi.sns.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record GetBunchOfReceiptServiceResponse(
        @JsonProperty("list")
        List<BriefReceiptInfo> bunchOfBriefReceiptInfo
) {

}
