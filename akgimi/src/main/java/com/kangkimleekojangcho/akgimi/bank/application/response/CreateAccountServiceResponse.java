package com.kangkimleekojangcho.akgimi.bank.application.response;

import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateAccountServiceResponse {
    private final String accountNumber;
    private final Boolean isPasswordRegistered;
    private final Integer bank;

}
