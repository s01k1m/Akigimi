package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.CommandTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MakeTransferService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final CommandAccountDbPort commandAccountDbPort;
    private final CommandTransferDbPort commandTrnsferDbPort;

    public void makeDepsoit(long userId, long amount){
        // 1. 유저에 대한 정보를 가져온다.
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 2. 유저의 출금 계좌를 가져온다.
        Account withdrawAccount = queryAccountDbPort.findByUserAndAccountType(user, AccountType.WITHDRAW)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        // 3. 유저의 입금 계좌를 가져온다.
        Account depositAccount = queryAccountDbPort.findByUserAndAccountType(user, AccountType.DEPOSIT)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        // 4. 유저의 출금 계좌에 amount 만큼 뺀다.
        withdrawAccount.withdraw(amount);
        // 5. 유저의 임금 계좌에 amount 만큼 더한다.
        depositAccount.deposit(amount);
        // 6. transfer에 대한 정보를 넣고 저장한다.
        Transfer transfer = Transfer.builder()
                .amount(amount)
                .sendAccount(withdrawAccount)
                .sendAccountBalance(withdrawAccount.getBalance())
                .receiveAccount(depositAccount)
                .receiveAccountBalance(withdrawAccount.getBalance())
                .build();
        commandAccountDbPort.save(withdrawAccount);
        commandAccountDbPort.save(depositAccount);
        commandTrnsferDbPort.save(transfer);
    }

    public void makeWithdraw(long userId, String userPassword){

    }

}
