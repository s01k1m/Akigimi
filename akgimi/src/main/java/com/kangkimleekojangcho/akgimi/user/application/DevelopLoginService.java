package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.adapter.out.UserJpaAdapter;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopLoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DevelopLoginService {
    private final UserJpaAdapter userJpaAdapter;
    private final JwtTokenIssuer jwtTokenIssuer;
    public DevelopLoginServiceResponse login(long userId) {
        User user = userJpaAdapter.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        String accessToken = jwtTokenIssuer.createAccessToken(userId,user.getUserState());
        String refreshToken = jwtTokenIssuer.createRefreshToken(userId,user.getUserState());
        return new DevelopLoginServiceResponse(accessToken, refreshToken);
    }
}
