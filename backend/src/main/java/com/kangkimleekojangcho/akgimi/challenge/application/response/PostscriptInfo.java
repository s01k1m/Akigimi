package com.kangkimleekojangcho.akgimi.challenge.application.response;

import com.querydsl.core.annotations.QueryProjection;

public record PostscriptInfo(
        Long userId,
        String userProfile,
        Long productId,
        String productName,
        String nickname,
        Integer price,
        String content,
        String photo,
        Integer period
) {

    @QueryProjection
    public PostscriptInfo {

    }
}
