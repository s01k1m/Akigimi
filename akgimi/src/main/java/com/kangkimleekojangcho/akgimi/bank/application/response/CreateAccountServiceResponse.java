package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Getter;

@Getter
public class CreateAccountServiceResponse {
    private final String accountNumger;
    private final Boolean isPasswordRegistered;

    public CreateAccountServiceResponse(String accountNumger, Boolean isPasswordRegistered) {
        this.accountNumger = accountNumger;
        this.isPasswordRegistered = isPasswordRegistered;
    }
}
