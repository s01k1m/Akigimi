package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CancelLikeRequest(

        @NotNull
        @Positive
        Long feedId
) {
}
