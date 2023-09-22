package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.CommandTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.HashPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QuerySaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class MakeTransferService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final CommandAccountDbPort commandAccountDbPort;
    private final CommandTransferDbPort commandTransferDbPort;
    private final QuerySaltPort querySaltPort;
    private final HashPort hashPort;

    @Transactional
    public void makeDeposit(long userId, long amount){
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
                .transferDateTime(LocalDateTime.now())
                .build();
        commandTransferDbPort.save(transfer);
    }

    @Transactional
    public void makeWithdraw(long userId, String userPassword){
        // 1. 유저에 대한 정보를 가져온다.
        User user = queryUserDbPort.findById(userId).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_USER));

        // 2. 유저에 대한 salt 정보를 가져온다.
        Salt salt = querySaltPort.findByUserIdAndSaltType(user.getId(), SaltType.SIMPLE).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NO_SALT));

        // 3. userPassword와 salt를 가지고 hash된 비밀번호를 가져온다.
        String hashUserPassword = hashPort.hash(userPassword, salt.getSaltValue());

        // 4. hashUserPassword와 user의 Password를 비교한다.
        if(!hashUserPassword.equals(user.getSimplePassword())){
            throw new BadRequestException(BadRequestExceptionCode.NOT_AVAILABLE_PASSWORD);
        }

        // 5. 유저의 출금 계좌를 가져온다.
        Account withdrawAccount = queryAccountDbPort.findByUserAndAccountType(user, AccountType.WITHDRAW)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));

        // 6. 유저의 입금 계좌를 가져온다.
        Account depositAccount = queryAccountDbPort.findByUserAndAccountType(user, AccountType.DEPOSIT)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        // 7. 입금 계좌의 전액을 가져온다.
        Long amount = depositAccount.getBalance();

        // 7. 유저의 입금 계좌에 amount 만큼 뺀다.
        depositAccount.withdraw(amount);
        // 8. 유저의 출금 계좌에 amount 만큼 더한다.
        withdrawAccount.deposit(amount);

        // 9. transfer에 대한 정보를 넣고 저장한다.
        Transfer transfer = Transfer.builder()
                .amount(amount)
                .sendAccount(depositAccount)
                .sendAccountBalance(depositAccount.getBalance())
                .receiveAccount(withdrawAccount)
                .receiveAccountBalance(withdrawAccount.getBalance())
                .transferDateTime(LocalDateTime.now())
                .build();
        commandTransferDbPort.save(transfer);
    }

}
