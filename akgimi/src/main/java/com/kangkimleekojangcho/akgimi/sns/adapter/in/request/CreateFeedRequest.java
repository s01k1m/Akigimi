package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import com.kangkimleekojangcho.akgimi.sns.application.request.CreateFeedServiceRequest;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CreateFeedRequest(
        @NotBlank(message = "미닝템을 입력해주세요.")
        String meaningItem,

        @Positive(message = "아끼신 금액을 정확히 입력해주세요.")
        Long saving,

        String akgimPlace,

        String photo,

        @Lob
        String content,

        Boolean isOpen
) {

    public CreateFeedServiceRequest toServiceRequest() {
        return CreateFeedServiceRequest.builder()
                .notPurchasedItem(this.meaningItem)
                .akgimPlace(this.akgimPlace)
                .content(this.content)
                .isOpen(this.isOpen)
                .photo(this.photo)
                .saving(this.saving)
                .build();
    }
}
