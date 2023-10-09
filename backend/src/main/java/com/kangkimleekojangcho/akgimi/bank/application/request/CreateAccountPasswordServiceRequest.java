package com.kangkimleekojangcho.akgimi.bank.application.request;

import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.Getter;

@Getter
public class CreateAccountPasswordServiceRequest {
    private Bank bank;
    private AccountType accountType;
    private String accountNumber;
    private String password;

    public CreateAccountPasswordServiceRequest(Bank bank, AccountType accountType,String accountNumber, String password) {
        this.bank = bank;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.password = password;
    }
}
