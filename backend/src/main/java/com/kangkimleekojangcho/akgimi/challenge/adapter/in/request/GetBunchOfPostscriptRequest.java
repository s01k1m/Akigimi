package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;


import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record GetBunchOfPostscriptRequest(
        @NotNull(message = "잘못된 요청입니다.")
        Long lastPostscriptId,
        @NotNull(message = "몇개의 리뷰를 받을 지 입력해주세요.")
        @Max(value = 100, message = "최대 요청 가능 개수는 100개 이하입니다.")
        @Min(value = 0, message = "잘못된 리뷰 요청 수입니다.")
        Integer numberOfPostscript,
        @NotNull(message = "원하는 제품을 입력해주세요.")
        @Positive(message = "잘못된 제품 요청입니다.")
        Long productId

) {
    public GetBunchOfPostscriptServiceRequest toServiceRequest() {
        return GetBunchOfPostscriptServiceRequest.builder()
                .lastPostscriptId(lastPostscriptId)
                .numberOfPostscript(numberOfPostscript)
                .productId(productId)
                .build();
    }
}
