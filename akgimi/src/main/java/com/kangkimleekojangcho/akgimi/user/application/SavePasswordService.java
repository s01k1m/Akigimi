package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandSaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.GenerateSaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.HashPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.request.SavePasswordServiceRequest;
import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SavePasswordService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final GenerateSaltPort generateSaltPort;
    private final HashPort hashPort;
    private final CommandSaltPort commandSaltPort;
    @Transactional
    public void save(Long userId, SavePasswordServiceRequest serviceRequest) {
        String rawSalt = generateSaltPort.generate();
        String hashed = hashPort.hash(serviceRequest.getPassword(), rawSalt);
        Account account = queryAccountDbPort.findById(serviceRequest.getAccountId()).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "존재하지 않는 계좌입니다."));
        account.setPassword(hashed, true);
//        Salt salt = new Salt(account, rawSalt);
//        commandSaltPort.save(salt);
    }
}

