package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.SignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final CommandUserDbPort commandUserDbPort;
    private final DecodeIdTokenService decodeIdTokenService;
    private final JwtTokenIssuer jwtTokenIssuer;
    private final QueryUserDbPort queryUserDbPort;
    @Transactional
    public SignUpServiceResponse signUp(String rawIdToken) {
        KakaoIdToken idToken = decodeIdTokenService.decode(rawIdToken);
        String oauthId = idToken.getSub();
        Optional<User> userOpt = queryUserDbPort.findByOauthId(oauthId);
        if (userOpt.isPresent()) {
            throw new BadRequestException(BadRequestExceptionCode.ALREADY_USER);
        }
        User user = User.builder()
                .oauthId(oauthId)
                .oidcProvider(OidcProvider.KAKAO)
                .userState(UserState.PENDING)
                .kakaoProfileNickname(new KakaoNickname(idToken.getNickname()))
                .build();
        user = commandUserDbPort.save(user);
        Long id = user.getId();
        String accessToken = jwtTokenIssuer.createAccessToken(id,user.getUserState());
        String refreshToken = jwtTokenIssuer.createRefreshToken(id,user.getUserState());
        return new SignUpServiceResponse(accessToken, refreshToken);
    }
}
