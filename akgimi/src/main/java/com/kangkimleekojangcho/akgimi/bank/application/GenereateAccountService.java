package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GenereateAccountService {

    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final CommandAccountDbPort commandAccountDbPort;
    private final RandomNumberPort randomNumberPort;

    public CreateAccountServiceResponse create(long userId, CreateAccountServiceRequest request) {
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        Optional<Account> account = queryAccountDbPort.findByUserAndAccountType(user, request.getAccountType());
        if (!account.isEmpty()) {
            // 기존 반환
            Account existAccount = queryAccountDbPort.findByUserAndAccountType(user, request.getAccountType())
                    .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
            return new CreateAccountServiceResponse(existAccount.getAccountNumber(), existAccount.getIsPasswordRegistered(), existAccount.getBank().ordinal());
        }
        // TODO 리펙토링 필요
        // 중복된 계좌 번호가 있는지 확인
        // ACOUNT 생성 로직과 GENERATE 생성로직이 따로 있어야 함.
        // 다른 서비스로 빼야한다.
        String randomGeneratedAccountNumber;

        while (true) {
            randomGeneratedAccountNumber = randomNumberPort.generateDigit(16);
            Optional<Account> checkAccount = queryAccountDbPort.findByAccountNumber(randomGeneratedAccountNumber, request.getAccountType());
            if (checkAccount.isEmpty()) {
                break;
            }
        }
        Account newAccount = commandAccountDbPort.save(Account.builder()
                .accountNumber(randomGeneratedAccountNumber)
                .accountType(request.getAccountType())
                .bank(request.getBank())
                .balance(0L)
                .isDeleted(false)
                .isPasswordRegistered(false)
                .user(user)
                .build()
        );
        if(request.getAccountType().equals(AccountType.WITHDRAW)){
            newAccount.deposit(5000000L);
        }

        return new CreateAccountServiceResponse(randomGeneratedAccountNumber, newAccount.getIsPasswordRegistered(), newAccount.getBank().ordinal() );
    }
}
