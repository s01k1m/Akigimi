package com.kangkimleekojangcho.akgimi.sns.application.response;

import com.querydsl.core.annotations.QueryProjection;

public record BriefFeedInfo(
        Long feedId,
        Long userId,
        String userProfile,
        String nickname,
        Long price,
//        Integer countOfLike,
        String notPurchasedItem,
        String akgimiPlace,
        String content,
        Boolean isLikedFeed,
        String photo
) {

    @QueryProjection
    public BriefFeedInfo {
    }
}