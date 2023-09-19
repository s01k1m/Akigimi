package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import com.kangkimleekojangcho.akgimi.sns.application.request.CreateFeedServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateFeedRequest(
        @NotBlank(message = "아낀 물품에 대한 이름을 입력해주세요.")
        String notPurchasedItem,

        @Positive(message = "아끼신 금액은 0 이상이어야 합니다.")
        Long saving,

        String akgimiPlace,
        String content,
        MultipartFile photo,
        @NotNull
        Boolean isPublic
) {

    public CreateFeedServiceRequest toServiceRequest() {
        return CreateFeedServiceRequest.builder()
                .notPurchasedItem(this.notPurchasedItem)
                .akgimiPlace(this.akgimiPlace)
                .content(this.content)
                .isPublic(this.isPublic)
                .photo(photo)
                .saving(this.saving)
                .build();
    }

}
