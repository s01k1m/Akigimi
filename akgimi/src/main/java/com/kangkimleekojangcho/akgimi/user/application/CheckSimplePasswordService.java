package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.HashPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QuerySaltPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckSimplePasswordService {
    private final HashPort hashPort;
    private final QuerySaltPort querySaltPort;
    private final QueryUserDbPort queryUserDbPort;

    @Transactional(readOnly = true)
    public boolean check(Long userId, String simplePassword) {
        Salt salt = querySaltPort.findByUserIdAndSaltType(userId, SaltType.SIMPLE).orElseThrow(() -> new UnauthorizedException(UnauthorizedExceptionCode.INVALID_SIMPLE_PASSWORD));
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        String hashed = hashPort.hash(simplePassword, salt.getSaltValue());
        return hashed.equals(user.getSimplePassword());
    }
}
