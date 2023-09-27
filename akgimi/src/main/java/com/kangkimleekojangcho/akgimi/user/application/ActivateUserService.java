package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.ActivateUserServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserField;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivateUserService {
    private final QueryUserDbPort queryUserDbPort;
    private final JwtTokenIssuer jwtTokenIssuer;

    @Transactional
    public ActivateUserServiceResponse activate(Long userId) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        List<UserField> userFields = user.whatIsNotFilled();
        if(userFields.isEmpty()){
            user.setUserState(UserState.ACTIVE);
            String accessToken = jwtTokenIssuer.createAccessTokenForUnauthorizedUser(user);
            String refreshToken = jwtTokenIssuer.createRefreshToken(user);
            return new ActivateUserServiceResponse(accessToken, refreshToken, userId);
        }
        throw new BadRequestException(BadRequestExceptionCode.CANNOT_ACTIVATE);
    }
}
