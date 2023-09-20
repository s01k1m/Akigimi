package com.kangkimleekojangcho.akgimi.sns.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record GetBunchOfFeedWrittenByFollowerServiceResponse(
        @JsonProperty("list")
        List<BriefFeedInfo> bunchOfBriefFeedInfo
) {
}
