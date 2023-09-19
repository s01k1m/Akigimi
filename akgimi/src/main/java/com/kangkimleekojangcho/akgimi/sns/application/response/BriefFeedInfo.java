package com.kangkimleekojangcho.akgimi.sns.application.response;

public record BriefFeedInfo(
        Long userId,
        String userProfile,
        Integer price,
        Integer likes,
        String notPurchasedItem,
        String akgimiPlace,
        String content,
        Boolean isLikedFeed,
        String photo
) {
}
