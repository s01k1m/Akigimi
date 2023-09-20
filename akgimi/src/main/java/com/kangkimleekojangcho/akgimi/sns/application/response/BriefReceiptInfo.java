package com.kangkimleekojangcho.akgimi.sns.application.response;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record BriefReceiptInfo(
        Long price,
        String notPurchasedItem,
        String akgimiPlace,
        LocalDateTime createdDateTime,
        String photo
) {

    @QueryProjection
    public BriefReceiptInfo {
    }
}
