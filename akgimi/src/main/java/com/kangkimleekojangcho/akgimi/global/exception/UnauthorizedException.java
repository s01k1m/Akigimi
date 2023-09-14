package com.kangkimleekojangcho.akgimi.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class UnauthorizedException extends HttpBusinessException {
    private final UnauthorizedExceptionCode code;
    private String customMessage;

    public UnauthorizedException(UnauthorizedExceptionCode code, String customMessage) {
        this.code = code;
        this.customMessage = customMessage;
    }

    @Override
    public String getCode() {
        return code.getCode();
    }

    @Override
    public String getMessage() {
        if(customMessage==null){
            return code.getDescriptionMessage();
        }
        return customMessage;
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

    public boolean isTokenExpired() {
        return code.isTokenExpired();
    }
}
