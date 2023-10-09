package com.kangkimleekojangcho.akgimi.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


public class BadRequestException extends HttpBusinessException {
    private final BadRequestExceptionCode code;
    private String customMessage;

    public BadRequestException(BadRequestExceptionCode code) {
        this.code = code;
    }

    public BadRequestException(BadRequestExceptionCode code, String customMessage) {
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
        return HttpStatus.BAD_REQUEST.value();
    }
}
