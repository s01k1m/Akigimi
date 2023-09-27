package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopLoginServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.DevelopSignUpServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.KakaoNickname;
import com.kangkimleekojangcho.akgimi.user.domain.OidcProvider;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DevelopLoginService {
    private final QueryUserDbPort queryUserDbPort;
    private final CommandUserDbPort commandUserDbPort;
    private final JwtTokenIssuer jwtTokenIssuer;
    public DevelopLoginServiceResponse login(long userId) {
        final String DEFAULT_PROFILE_IMAGE_URL = "https://postfiles.pstatic.net/MjAyMDAyMTBfODAg/MDAxNTgxMzA0MTE3ODMy.ACRLtB9v5NH-I2qjWrwiXLb7TeUiG442cJmcdzVum7cg.eTLpNg_n0rAS5sWOsofRrvBy0qZk_QcWSfUiIagTfd8g.JPEG.lattepain/1581304118739.jpg?type=w580";
        Optional<User> userOpt = queryUserDbPort.findById(userId);
        User user;
        if (userOpt.isEmpty()) {
            user = User.builder()
                    .userState(UserState.PENDING)
                    .profileImageUrl(DEFAULT_PROFILE_IMAGE_URL)
                    .build();
            commandUserDbPort.save(user);
            user.setKakaoProfileNickname(String.valueOf(user.getId()));
        } else{
            user = userOpt.get();
        }
        String accessToken = jwtTokenIssuer.createAccessTokenForUnauthorizedUser(user);
        String refreshToken = jwtTokenIssuer.createRefreshToken(user);
        return new DevelopLoginServiceResponse(accessToken, refreshToken, user);
    }
}
