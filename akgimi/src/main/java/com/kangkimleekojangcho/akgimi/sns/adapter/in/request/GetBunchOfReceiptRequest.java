package com.kangkimleekojangcho.akgimi.sns.adapter.in.request;

import com.kangkimleekojangcho.akgimi.sns.application.request.GetBunchOfReceiptServiceRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GetBunchOfReceiptRequest (
        @NotNull(message = "올바른 feed 요청을 해주세요")
        Long lastReceiptId,
        @Positive(message = "올바른 개수를 입력해주세요.")
        @NotNull(message = "올바른 개수를 입력해주세요.") //TODO: 기본 개수 입력시켜주기.
        @Max(value = 100L, message = "피드 요청 수는 최대 100개 입니다.")
        Integer numberOfReceipt
) {
        public GetBunchOfReceiptServiceRequest toServiceRequest() {
                return GetBunchOfReceiptServiceRequest.builder()
                        .lastReceiptId(lastReceiptId)
                        .numberOfReceipt(numberOfReceipt)
                        .build();
        }
}
