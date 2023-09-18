package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountPort;
import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenerateDepositAccountService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountPort queryAccountPort;
    private final CommandAccountPort commandAccountPort;
    private final RandomNumberPort randomNumberPort;
    public CreateAccountServiceResponse create(long userId, Bank bank) {
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        Optional<Account> account = queryAccountPort.findByUserAndAccountType(user, AccountType.DEPOSIT);
        String accountNumber = randomNumberPort.generateDigit(16);
        if(account.isEmpty()){
            // 중복된 계좌 번호가 있는지 확인
            for(int i=0; i<10; i++){
                Optional<Account> checkAccount = queryAccountPort.findByAccountNumber(accountNumber, AccountType.DEPOSIT);
                if(checkAccount.isEmpty()){
                    break;
                }
                accountNumber = randomNumberPort.generateDigit(16);
            }
            commandAccountPort.add(Account.builder()
                    .accountNumber(accountNumber)
                    .accountType(AccountType.DEPOSIT)
                    .bank(bank)
                    .balance(0L)
                    .isDeleted(false)
                    .isPasswordRegistered(false)
                    .build()
            );
        }else{
            // 기존 반환
            Account existAccount = queryAccountPort.findByUserAndAccountType(user, AccountType.DEPOSIT)
                    .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
            accountNumber = existAccount.getAccountNumber();
        }
        return new CreateAccountServiceResponse(accountNumber,false);
    }
}
