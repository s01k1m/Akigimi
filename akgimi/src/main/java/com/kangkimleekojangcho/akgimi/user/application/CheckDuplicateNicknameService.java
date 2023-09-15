package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckDuplicateNicknameService {
    private final QueryUserDbPort queryUserDbPort;
    public boolean check(String rawNickname) {
        Nickname nickname = new Nickname(rawNickname);
        return queryUserDbPort.existsByNickname(nickname.getValue());
    }
}
