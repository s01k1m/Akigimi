package com.kangkimleekojangcho.akgimi.sns.application.request;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.Lob;
import lombok.Builder;

@Builder
public record CreateFeedServiceRequest(
        String notPurchasedItem,

        Long saving,

        String akgimPlace,

        String photo,
        @Lob
        String content,

        Boolean isOpen
) {

    public Feed toEntity(Account depositAccount, User user, Challenge challenge) {
        return Feed.builder()
                .user(user)
                .challenge(challenge)
                .accumulatedAmount(depositAccount.getBalance())
                .place(akgimPlace)
                .price(saving)
                .isPublic(isOpen)
                .isDeleted(false)
                .notPurchasedItem(notPurchasedItem)
                .content(content)
                .build();
    }
}
