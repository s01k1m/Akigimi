package com.kangkimleekojangcho.akgimi.challenge.application.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetBunchOfPostscriptServiceResponse(
        List<PostscriptInfo> bunchOfPostscriptInfo
) {

}
