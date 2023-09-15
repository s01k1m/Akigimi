package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import com.kangkimleekojangcho.akgimi.user.application.response.GenerateWithdrawalAccountServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateWithdrawalAccountService {
    private final QueryUserDbPort queryUserDbPort;
    private final CreateAccountService createAccountService;
    private final RandomNumberPort randomNumberPort;
    private final GenerateRandomAccountNumberService generateRandomAccountNumberService;
    private final CommandAccountPort commandAccountPort;
    public GenerateWithdrawalAccountServiceResponse generate(Long userId) {
        Bank bank = Bank.pick(randomNumberPort.generate(0, Bank.howMany()));
        String accountNumber = generateRandomAccountNumberService.generate();
        Account account = Account.builder()
                .accountType(AccountType.WITHDRAW)
                .accountNumber(accountNumber)
                .bank(bank)
                .balance(100000L).build();
        return GenerateWithdrawalAccountServiceResponse.from(account);
    }
}
