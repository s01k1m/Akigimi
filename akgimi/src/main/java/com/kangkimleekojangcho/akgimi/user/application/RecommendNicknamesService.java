package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.RandomNumberPort;
import com.kangkimleekojangcho.akgimi.user.application.response.RecommendNicknamesServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.Nickname;
import com.kangkimleekojangcho.akgimi.user.domain.RecommendNicknamePrefix;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RecommendNicknamesService {
    private final RandomNumberPort randomNumberPort;
    private final QueryUserDbPort userDbPort;
    public RecommendNicknamesServiceResponse recommend(long userId) {
        int randomIdx = randomNumberPort.generate(0, RecommendNicknamePrefix.howMany());
        String prefix = RecommendNicknamePrefix.pick(randomIdx);
        User user = userDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        String kakaoProfileNickname = user.getKakaoProfileNickname();
        String randomPostfix = randomNumberPort.generateDigit(10);
        Nickname nickname = new Nickname(prefix + ' ' + kakaoProfileNickname + randomPostfix);
        return new RecommendNicknamesServiceResponse(nickname.getValue()); // 추천하는 닉네임이 이미 겹치는 경우, 이를 해결할 로직이 필요함. (뒤에 랜덤한 숫자를 붙이는 식으로 하면 어떨까?) TODO
    }
}
