package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.*;
import com.kangkimleekojangcho.akgimi.user.domain.Salt;
import com.kangkimleekojangcho.akgimi.user.domain.SaltType;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SetSimplePasswordService {
    private final HashPort hashPort;
    private final CommandSaltPort commandSaltPort;
    private final QueryUserDbPort queryUserDbPort;
    private final GenerateSaltPort generateSaltPort;

    public void set(Long userId, String simplePassword) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        String rawSalt = generateSaltPort.generate();
        String hashed = hashPort.hash(simplePassword, rawSalt);
        Salt salt = new Salt(user, rawSalt, SaltType.SIMPLE);
        user.setSimplePassword(hashed);
        commandSaltPort.save(salt);
    }
}
