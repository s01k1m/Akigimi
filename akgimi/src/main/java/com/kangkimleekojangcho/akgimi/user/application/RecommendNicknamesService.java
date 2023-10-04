package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
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
        Nickname nickname=null;
        int i;
        for(i = 0; i<10; i++){
            int randomIdx = randomNumberPort.generate(0, RecommendNicknamePrefix.howMany());
            String prefix = RecommendNicknamePrefix.pick(randomIdx);
            User user = userDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
            String kakaoProfileNickname = user.getKakaoProfileNickname();
            kakaoProfileNickname = kakaoProfileNickname.substring(0,Math.min(kakaoProfileNickname.length(),3));
            String randomPostfix = randomNumberPort.generateDigit(10);
            nickname = new Nickname(prefix + ' ' + kakaoProfileNickname + randomPostfix);
            if(!userDbPort.existsByNickname(nickname.getValue())){
                break;
            }
        }
        if(i==10){
            throw new ServerErrorException(ServerErrorExceptionCode.CANNOT_GENERATE_RANDOM_NICKNAME);
        }
        return new RecommendNicknamesServiceResponse(nickname.getValue());
    }
}
