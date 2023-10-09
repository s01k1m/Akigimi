package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class CreateAccountRequest {
    private AccountType accountType;
    private Bank bank;
}
