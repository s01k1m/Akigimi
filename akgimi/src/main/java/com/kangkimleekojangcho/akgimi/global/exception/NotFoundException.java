package com.kangkimleekojangcho.akgimi.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class NotFoundException extends HttpBusinessException {
    private final NotFoundExceptionCode code;
    private String customMessage;

    public NotFoundException(NotFoundExceptionCode code, String customMessage) {
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
        return HttpStatus.NOT_FOUND.value();
    }

}
