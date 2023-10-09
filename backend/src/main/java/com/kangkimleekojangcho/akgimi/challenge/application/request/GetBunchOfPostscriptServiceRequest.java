package com.kangkimleekojangcho.akgimi.challenge.application.request;

import lombok.Builder;

@Builder
public record GetBunchOfPostscriptServiceRequest(
        Long lastPostscriptId,
        Integer numberOfPostscript,
        Long productId
) {
}
