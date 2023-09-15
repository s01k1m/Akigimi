package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountPasswordServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountPasswordService {
    public CreateAccountPasswordServiceResponse createAccountPassword(long userId, Bank bank, String accountNumber, String password) {
        return null;
    }
}
