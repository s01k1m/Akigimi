package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.jwt.application.ConvertToJwtTokenService;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.ReissueServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import com.kangkimleekojangcho.akgimi.user.domain.RefreshToken;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final ConvertToJwtTokenService convertToJwtTokenService;
    private final JwtTokenIssuer jwtTokenIssuer;
    private final QueryUserDbPort queryUserDbPort;
    public ReissueServiceResponse reissue(String rawRefreshToken) {
        RefreshToken refreshToken = convertToJwtTokenService.convertToRefreshToken(rawRefreshToken);
        checkRefreshTokenIsNotExpired(refreshToken);
        User user = getUser(refreshToken);
        String accessToken = jwtTokenIssuer.createAccessTokenForAuthorizedUser(user);
        return new ReissueServiceResponse(accessToken);
    }

    private User getUser(RefreshToken refreshToken) {
        User user = queryUserDbPort.findById(refreshToken.getUserId()).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.ALREADY_USER));
        return user;
    }

    private static void checkRefreshTokenIsNotExpired(RefreshToken refreshToken) {
        if(refreshToken.isExpired(new Date())){
            throw new UnauthorizedException(UnauthorizedExceptionCode.TOKEN_EXPIRED);
        }
    }
}
