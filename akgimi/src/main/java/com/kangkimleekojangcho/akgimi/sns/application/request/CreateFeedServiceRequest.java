package com.kangkimleekojangcho.akgimi.sns.application.request;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.persistence.Lob;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateFeedServiceRequest(
        String notPurchasedItem,

        Long saving,

        String akgimiPlace,
        MultipartFile photo,
        @Lob
        String content,

        Boolean isPublic
) {

    public Feed toEntity(Account depositAccount, User user, Challenge challenge) {
        return Feed.builder()
                .user(user)
                .challenge(challenge)
                .accumulatedAmount(depositAccount.getBalance())
                .place(akgimiPlace)
                .price(saving)
                .isPublic(isPublic)
                .isDeleted(false)
                .notPurchasedItem(notPurchasedItem)
                .content(content)
                .build();
    }
}
