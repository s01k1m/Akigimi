package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.jwt.application.CreateJwtTokenService;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandBlackListPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryBlackListPort;
import com.kangkimleekojangcho.akgimi.user.application.request.LogoutServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final CommandBlackListPort commandBlackListPort;
    private final CreateJwtTokenService createJwtTokenService;
    public void logout(LogoutServiceRequest serviceRequest) {
        String accessToken = serviceRequest.getAccessToken();
        String refreshToken = serviceRequest.getRefreshToken();
        long accessTokenTime = createJwtTokenService.create(accessToken).getExpiredAt().getTime() - new Date().getTime();
        long refreshTokenTime = createJwtTokenService.create(refreshToken).getExpiredAt().getTime() - new Date().getTime();
        if(accessTokenTime>0){
            commandBlackListPort.add(accessToken, accessTokenTime);
        }
        if(refreshTokenTime>0){
            commandBlackListPort.add(refreshToken, refreshTokenTime);
        }
    }
}
