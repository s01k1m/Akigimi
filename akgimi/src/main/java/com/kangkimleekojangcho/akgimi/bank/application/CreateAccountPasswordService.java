package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountPasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountPasswordServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.GenerateSaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.HashPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;


@Service
@RequiredArgsConstructor
public class CreateAccountPasswordService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final CommandAccountDbPort commandAccountDbPort;
    private final GenerateSaltPort generateSaltPort;
    private final HashPort hashPort;
    public CreateAccountPasswordServiceResponse createAccountPassword(long userId, CreateAccountPasswordServiceRequest request) {
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 1. bank, accountType, accountNumber가 일치하는 계좌를 찾는다.
        Account account = queryAccountDbPort.findByUserIdAndAccountTypeAndAccountNumber(userId, request.getAccountType(), request.getAccountNumber())
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        // 2. Account의 isPasswordRegistered가 false인지 확인한다.
        if(account.getIsPasswordRegistered()){
           return new CreateAccountPasswordServiceResponse(false);
        }
        // 3. password를 salt처리한다.
        String accountSalt = generateSaltPort.generate();
        // 4. password+salt하여 hashing 처리한다.
        String digest = hashPort.hash(request.getPassword(),accountSalt);

        // 5. accountSalt를 저장한다.

        // 6. Account의 계좌 password에 digest를 저장한다.

        return new CreateAccountPasswordServiceResponse(true);
    }

}
