package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountService {

    public CreateAccountServiceResponse createAccount(long userId, String accountType, Bank bank) {
        return null;
    }
}
