package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateAccountServiceResponse {
    private final String accountNumber;
    private final Boolean isPasswordRegistered;

}
