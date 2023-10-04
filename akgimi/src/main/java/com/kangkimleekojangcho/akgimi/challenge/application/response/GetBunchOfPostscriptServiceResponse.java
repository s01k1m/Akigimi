package com.kangkimleekojangcho.akgimi.challenge.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record GetBunchOfPostscriptServiceResponse(
        @JsonProperty("list")
        List<PostscriptInfo> bunchOfPostscriptInfo
) {

}
