package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.GenerateWithdrawalAccountServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateWithdrawalAccountService {
    private final QueryUserDbPort queryUserDbPort;
    public GenerateWithdrawalAccountServiceResponse generate(Long userId) {
        Account account = Account.builder()
                .bank(Bank.SSAFY)
                .accountNumber("AccountNumber")
                .build();
        return GenerateWithdrawalAccountServiceResponse.from(account);
    }
}
