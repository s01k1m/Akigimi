package com.kangkimleekojangcho.akgimi.sns.application.request;

import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CreateFeedServiceRequest(
        String meaningItem,

        Long saving,

        String akgimPlace,

        String photo,

        @Lob
        String content,

        Boolean isOpen
) {

}
