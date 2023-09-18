package com.kangkimleekojangcho.akgimi.bank.application;

import com.kangkimleekojangcho.akgimi.bank.application.request.CreateAccountPasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.bank.application.response.CreateAccountPasswordServiceResponse;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.Bank;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountPasswordService {
    private final QueryUserDbPort queryUserDbPort;
    public CreateAccountPasswordServiceResponse createAccountPassword(long userId, CreateAccountPasswordServiceRequest request) {
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 1. bank, accountType, accountNumber가 일치하는 계좌를 찾는다.
        // 2. Account의 isPasswordRegistered가 false인지 확인한다.
        // 3. Account의 게좌 비밀번호를 저장한다.
        return null;
    }
}
