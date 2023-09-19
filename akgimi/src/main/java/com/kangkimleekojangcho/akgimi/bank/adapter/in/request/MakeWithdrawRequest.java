package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MakeWithdrawRequest {
    private final String userPassword;
}
