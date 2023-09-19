package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryIdTokenPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.request.AddDataForPendingUserServiceRequest;
import com.kangkimleekojangcho.akgimi.user.application.response.AddDataForPendingUserServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddDataForPendingUserService {
    private final QueryUserDbPort queryUserDbPort;
    private final JwtTokenIssuer jwtTokenIssuer;

    @Transactional
    public AddDataForPendingUserServiceResponse addDataForPendingUser(long userId, AddDataForPendingUserServiceRequest request) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        if(user.isSignUpSccessfully()){
            throw new BadRequestException(BadRequestExceptionCode.ALREADY_USER);
        }

        String accessToken = jwtTokenIssuer.createAccessToken(userId);
        String refreshToken = jwtTokenIssuer.createRefreshToken(userId);
        user.signUp();
        return new AddDataForPendingUserServiceResponse(accessToken, refreshToken);
    }
}
