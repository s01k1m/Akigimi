package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.jwt.application.ConvertToJwtTokenService;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandBlackListPort;
import com.kangkimleekojangcho.akgimi.user.application.request.LogoutServiceRequest;
import com.kangkimleekojangcho.akgimi.user.domain.AccessToken;
import com.kangkimleekojangcho.akgimi.user.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final CommandBlackListPort commandBlackListPort;
    private final ConvertToJwtTokenService convertToJwtTokenService;
    public void logout(LogoutServiceRequest serviceRequest) {
        String rawAccessToken = serviceRequest.getAccessToken();
        String rawRefreshToken = serviceRequest.getRefreshToken();
        AccessToken accessToken = convertToJwtTokenService.convertToAccessToken(rawAccessToken);
        RefreshToken refreshToken = convertToJwtTokenService.convertToRefreshToken(rawRefreshToken);
        registerAccessTokenToBlackList(accessToken);
        registerRefreshTokenToBlackList(refreshToken);
    }

    private void registerRefreshTokenToBlackList(RefreshToken refreshToken) {
        long refreshTokenTime = refreshToken.getExpiredAt().getTime() - new Date().getTime();
        if(refreshTokenTime>0){
            commandBlackListPort.add(refreshToken.getRawToken(), refreshTokenTime);
        }
    }

    private void registerAccessTokenToBlackList(AccessToken accessToken) {
        long accessTokenTime = accessToken.getExpiredAt().getTime() - new Date().getTime();
        if(accessTokenTime>0){
            commandBlackListPort.add(accessToken.getRawToken(), accessTokenTime);
        }
    }
}
