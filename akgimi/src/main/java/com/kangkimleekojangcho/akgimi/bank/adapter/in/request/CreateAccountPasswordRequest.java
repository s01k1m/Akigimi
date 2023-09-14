package com.kangkimleekojangcho.akgimi.bank.adapter.in.request;

import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.Getter;

@Getter
public class CreateAccountPasswordRequest {
    private Bank bank;
    private String accountNumber;
    private String password;
}
