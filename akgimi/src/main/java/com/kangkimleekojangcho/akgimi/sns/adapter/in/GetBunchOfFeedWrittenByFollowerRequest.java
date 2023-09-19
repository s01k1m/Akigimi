package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfFeedWrittenByFollowerRequestServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record GetBunchOfFeedWrittenByFollowerRequest(
        @NotNull(message = "올바른 feed 요청을 해주세요")
        Long lastFeedId,
        @Positive(message = "올바른 개수를 입력해주세요.")
        @NotNull(message = "올바른 개수를 입력해주세요.") //TODO: 기본 개수 입력시켜주기.
        @Max(value = 100L, message = "피드 요청 수는 최대 100개 입니다.")
        Integer numberOfFeed
) {

    public GetBunchOfFeedWrittenByFollowerRequestServiceRequest toServiceRequest() {
        return GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                .lastFeedId(lastFeedId)
                .numberOfFeed(numberOfFeed)
                .build();
    }
}
