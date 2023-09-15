package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Nickname;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class InputNicknameService {
    private final QueryUserDbPort queryUserDbPort;
    @Transactional
    public void input(long userId, String rawNickname) {
        // Nickname 기본적인 유효성 검사를 해야 함.
        // Nickname 클래스의 생성자에게 유효성 검사를 맡긴다.
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        Nickname nickname = new Nickname(rawNickname);
        if(queryUserDbPort.existsByNickname(nickname.getValue())){
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT, "이미 존재하는 닉네임입니다.");
        }
        user.setNickname(nickname.getValue());
    }
}
