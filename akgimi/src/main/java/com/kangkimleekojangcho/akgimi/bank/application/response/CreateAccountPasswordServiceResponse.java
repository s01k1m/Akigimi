package com.kangkimleekojangcho.akgimi.bank.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateAccountPasswordServiceResponse {
    private final Boolean isPasswordRegistered;
}
