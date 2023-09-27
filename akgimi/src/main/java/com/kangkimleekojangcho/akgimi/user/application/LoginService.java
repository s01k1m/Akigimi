package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.LoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final CommandUserDbPort commandUserDbPort;
    private final QueryUserDbPort queryUserDbPort;
    private final DecodeIdTokenService decodeIdTokenService;
    private final JwtTokenIssuer jwtTokenIssuer;

    public LoginServiceResponse login(String rawIdToken) {
        KakaoIdToken idToken = decodeIdTokenService.decode(rawIdToken);
        String oauthId = idToken.getSub();
        User user = queryUserDbPort.findByOauthId(oauthId)
                .orElse(User.builder()
                        .oauthId(oauthId)
                        .oidcProvider(OidcProvider.KAKAO)
                        .userState(UserState.PENDING)
                        .kakaoProfileNickname(new KakaoNickname(idToken.getNickname()))
                        .profileImageUrl(idToken.getProfileImageUrl())
                        .build());
        user = commandUserDbPort.save(user);
        String accessToken = jwtTokenIssuer.createAccessTokenForUnauthorizedUser(user);
        String refreshToken = jwtTokenIssuer.createRefreshToken(user);
        return new LoginServiceResponse(accessToken, refreshToken, user);
    }
}
