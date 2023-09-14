package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckDuplicateNicknameService {
    private final QueryUserDbPort queryUserDbPort;
    public boolean check(String nickname) {
        return queryUserDbPort.existsByNickname(nickname);
    }
}
