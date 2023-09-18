package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountPasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.Getter;

@Getter
public class CreateAccountPasswordRequest {
    private Bank bank;
    private AccountType accountType;
    private String accountNumber;
    private String password;

    public CreateAccountPasswordServiceRequest toServiceRequest() {
        return new CreateAccountPasswordServiceRequest(bank,accountType,accountNumber,password);
    }
}
