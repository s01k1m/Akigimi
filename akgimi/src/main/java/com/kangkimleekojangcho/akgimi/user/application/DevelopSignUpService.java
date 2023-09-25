package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopSignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.OidcProvider;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DevelopSignUpService {
    private final CommandUserDbPort commandUserDbPort;
    private final JwtTokenIssuer jwtTokenIssuer;
    @Transactional
    public DevelopSignUpServiceResponse signUp() {
        User user = User.builder()
                .userState(UserState.PENDING)
                .profileImageUrl("https://postfiles.pstatic.net/MjAyMDAyMTBfODAg/MDAxNTgxMzA0MTE3ODMy.ACRLtB9v5NH-I2qjWrwiXLb7TeUiG442cJmcdzVum7cg.eTLpNg_n0rAS5sWOsofRrvBy0qZk_QcWSfUiIagTfd8g.JPEG.lattepain/1581304118739.jpg?type=w580")
                .build();
        user = commandUserDbPort.save(user);
        user.setKakaoProfileNickname("kakao"+user.getId());
        String accessToken = jwtTokenIssuer.createAccessToken(user.getId(), user.getUserState());
        String refreshToken = jwtTokenIssuer.createRefreshToken(user.getId(), user.getUserState());
        return DevelopSignUpServiceResponse.from(user, accessToken, refreshToken);
    }
}
