package com.kangkimleekojangcho.akgimi.sns.application.request;

import lombok.Builder;

@Builder
public record GetBunchOfReceiptServiceRequest(
        Long lastReceiptId,
        Integer numberOfReceipt
) {
}
