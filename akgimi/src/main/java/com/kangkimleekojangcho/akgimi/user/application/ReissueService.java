package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.jwt.application.CreateJwtTokenService;
import com.kangkimleekojangcho.akgimi.user.application.response.ReissueServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final CreateJwtTokenService createJwtTokenService;
    private final JwtTokenIssuer jwtTokenIssuer;
    public ReissueServiceResponse reissue(String rawRefreshToken) {
        JwtToken refreshToken = createJwtTokenService.create(rawRefreshToken);
        if(refreshToken.isAccessToken()){
            throw new UnauthorizedException(UnauthorizedExceptionCode.NOT_REFRESH_TOKEN);
        }
        if(refreshToken.isExpired(new Date())){
            throw new UnauthorizedException(UnauthorizedExceptionCode.TOKEN_EXPIRED);
        }
        String accessToken = jwtTokenIssuer.createAccessToken(refreshToken.getUserId(),refreshToken.getUserState());
        return new ReissueServiceResponse(accessToken);
    }
}
