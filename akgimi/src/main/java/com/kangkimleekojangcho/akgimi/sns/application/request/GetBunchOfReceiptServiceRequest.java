package com.kangkimleekojangcho.akgimi.sns.application.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record GetBunchOfReceiptServiceRequest(

        Long lastReceiptId,
        @Max(value = 101L, message = "영수증 요청 수는 최대 100개 입니다.")
        Integer numberOfReceipt
) {
}
