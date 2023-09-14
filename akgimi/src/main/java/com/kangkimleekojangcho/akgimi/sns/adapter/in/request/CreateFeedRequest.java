package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class CreateFeedRequest {

    @NotBlank(message = "미닝템을 입력해주세요.")
    private String meaningItem;

    @Positive(message = "아끼신 금액을 정확히 입력해주세요.")
    @Max(value = Long.MAX_VALUE, message = "아끼신 금액을 정확히 입력해주세요")
    private Long saving;

    private String akgimPlace;

    private String photo;

    @Lob
    private String content;

    private Boolean isOpen;

    @Builder
    public CreateFeedRequest(String meaningItem, @NotNull Long saving, String akgimPlace,
                             String photo, String content, Boolean isOpen) {
        this.meaningItem = meaningItem;
        this.saving = saving;
        this.akgimPlace = akgimPlace;
        this.photo = photo;
        this.content = content;
        this.isOpen = isOpen;
    }
}
