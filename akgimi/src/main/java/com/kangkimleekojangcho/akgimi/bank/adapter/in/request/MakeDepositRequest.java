package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MakeDepositRequest {
    private final Long amount;
}
