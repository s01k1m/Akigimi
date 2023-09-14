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

@Builder
public record CreateFeedRequest (
        @NotBlank(message = "미닝템을 입력해주세요.")
        String meaningItem,

        @Positive(message = "아끼신 금액을 정확히 입력해주세요.")
        Long saving,

        String akgimPlace,

        String photo,

        @Lob
        String content,

        Boolean isOpen
){

}
