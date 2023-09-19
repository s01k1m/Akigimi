package com.kangkimleekojangcho.akgimi.sns.application.request;

import lombok.Builder;

@Builder
public record GetBunchOfFeedWrittenByFollowerRequestServiceRequest(
        Long lastFeedId,
        Integer numberOfFeed
) {

}
