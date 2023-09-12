package com.kangkimleekojangcho.akgimi.jwt.application;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExtractTokenStringService {
    public String extract(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.NO_TOKEN);
        }
        return authorizationHeader.substring(7);
    }
}
