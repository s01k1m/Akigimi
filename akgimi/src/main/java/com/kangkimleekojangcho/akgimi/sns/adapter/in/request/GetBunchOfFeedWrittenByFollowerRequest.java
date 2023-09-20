package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfFeedWrittenByFollowerRequestServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record GetBunchOfFeedWrittenByFollowerRequest(
        @Positive
        @NotNull(message = "올바른 feed 요청을 해주세요")
        Long lastFeedId,
        @Min(value = 0, message = "올바른 개수를 입력해주세요.")
        @NotNull(message = "올바른 개수를 입력해주세요.") //TODO: 기본 개수 입력시켜주기.
        Integer numberOfFeed
) {
    public GetBunchOfFeedWrittenByFollowerRequestServiceRequest toServiceRequest() {
        return GetBunchOfFeedWrittenByFollowerRequestServiceRequest.builder()
                .lastFeedId(lastFeedId)
                .numberOfFeed(numberOfFeed)
                .build();
    }
}
