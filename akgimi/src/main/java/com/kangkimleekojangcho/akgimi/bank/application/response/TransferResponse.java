package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TransferResponse {
    private LocalDateTime transferDateTime;
    private String type;
    private long amount;
}
