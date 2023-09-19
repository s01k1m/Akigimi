package com.kangkimleekojangcho.akgimi.bank.application.request;

import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.Getter;

@Getter
public class CreateAccountServiceRequest {
    private final Bank bank;
    private final AccountType accountType;

    public CreateAccountServiceRequest(Bank bank, AccountType accountType) {
        this.bank = bank;
        this.accountType = accountType;
    }
}
