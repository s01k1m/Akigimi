package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class CheckDepositWithDrawServiceResponse {
    private final String transfer;
    private final List<TransferResponse> depositWithDrawResponse;
}
