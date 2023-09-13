package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryIdTokenPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoIdToken;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final QueryUserDbPort queryUserDbPort;
    private final DecodeIdTokenService decodeIdTokenService;
    private final JwtTokenIssuer jwtTokenIssuer;

    public LoginServiceResponse login(String rawIdToken) {
        KakaoIdToken idToken = decodeIdTokenService.decode(rawIdToken);
        String oauthId = idToken.getSub();
        User user = queryUserDbPort.findByOauthId(oauthId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        String accessToken = jwtTokenIssuer.createAccessToken(user.getId());
        String refreshToken = jwtTokenIssuer.createRefreshToken(user.getId());
        return new LoginServiceResponse(accessToken, refreshToken);
    }
}
