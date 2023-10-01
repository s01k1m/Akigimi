package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record MarkLikeToFeedRequest(
        @Positive
        Long feedId
) {
}
