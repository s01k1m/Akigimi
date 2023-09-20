package com.kangkimleekojangcho.akgimi.sns.application.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetBunchOfFeedWrittenByFollowerServiceResponse(
    List<BriefFeedInfo> bunchOfBriefFeedInfo
)
{
}
