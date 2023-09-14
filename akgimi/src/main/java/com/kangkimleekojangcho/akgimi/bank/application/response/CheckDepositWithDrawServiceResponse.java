package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CheckDepositWithDrawServiceResponse {
    private String transfer;
    private List<TransferResponse> depositWithDrawResponse;
}
