package com.kangkimleekojangcho.akgimi.sns.application.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record GetBunchOfFeedWrittenByFollowerRequestServiceRequest(
        Long lastFeedId,
        @Max(value = 100, message = "피드 요청은 최대 100개까지만 가능합니다")
        Integer numberOfFeed
) {

}
