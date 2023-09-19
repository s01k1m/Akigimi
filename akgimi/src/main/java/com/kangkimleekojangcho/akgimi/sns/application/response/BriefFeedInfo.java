package com.kangkimleekojangcho.akgimi.sns.application.response;

import com.querydsl.core.annotations.QueryProjection;

public record BriefFeedInfo(
        Long userId,
//        String userProfile,
        Long price,
//        Integer likes,
        String notPurchasedItem,
        String akgimiPlace,
        String content,
//        Boolean isLikedFeed,
        String photo
) {

    @QueryProjection //querydsl에 dto 자체가 종속됨.
    public BriefFeedInfo(Long userId, Long price,
                         String notPurchasedItem, String akgimiPlace,
                         String content, String photo) {
        this.userId = userId;
        this.price = price;
        this.notPurchasedItem = notPurchasedItem;
        this.akgimiPlace = akgimiPlace;
        this.content = content;
        this.photo = photo;
    }
}
