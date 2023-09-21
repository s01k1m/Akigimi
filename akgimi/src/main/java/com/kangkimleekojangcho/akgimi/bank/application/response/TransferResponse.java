package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class TransferResponse {
    private final LocalDateTime transferDateTime;
    private final String type;
    private final Long amount;
    private final Long balance;
}
