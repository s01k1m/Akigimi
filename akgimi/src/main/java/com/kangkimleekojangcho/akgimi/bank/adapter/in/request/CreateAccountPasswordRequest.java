package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountPasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateAccountPasswordRequest {
    private final Bank bank;
    private final AccountType accountType;
    private final String accountNumber;
    private final String password;

    public CreateAccountPasswordServiceRequest toServiceRequest() {
        return new CreateAccountPasswordServiceRequest(bank,accountType,accountNumber,password);
    }
}
