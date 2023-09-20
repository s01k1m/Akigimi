package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.response.CheckBalanceServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckBalanceService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    public CheckBalanceServiceResponse checkBalance(long userId, AccountType accountType) {
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        Account account = queryAccountDbPort.findByUserAndAccountType(user,accountType)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        return new CheckBalanceServiceResponse(account.getBalance());
    }
}
