package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.adapter.out.GetIdTokenAdapter;
import com.kangkimleekojangcho.akgimi.user.adapter.out.UserJpaAdapter;
import com.kangkimleekojangcho.akgimi.user.application.response.SignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoIdToken;
import com.kangkimleekojangcho.akgimi.user.domain.OauthProvider;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserJpaAdapter userJpaAdapter;
    private final GetIdTokenAdapter getIdTokenAdapter;
    private final DecodeIdTokenService decodeIdTokenService;
    private final JwtTokenIssuer jwtTokenIssuer;
    public SignUpServiceResponse signUp(String rawIdToken) {
        KakaoIdToken idToken = decodeIdTokenService.decode(rawIdToken);
        String oauthId = idToken.getSub();
        User user = User.builder()
                .oauthId(oauthId)
                .oauthProvider(OauthProvider.KAKAO)
                .userState(UserState.PENDING)
                .build();
        user = userJpaAdapter.save(user);
        Long id = user.getId();
        String accessToken = jwtTokenIssuer.createAccessToken(id,user.getUserState());
        String refreshToken = jwtTokenIssuer.createRefreshToken(id,user.getUserState());
        return new SignUpServiceResponse(accessToken, refreshToken);
    }
}
