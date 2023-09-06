package com.example.demo.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class BadRequestException extends HttpBusinessException {
    private final BadRequestExceptionCode customCode;
    @Override
    String getCustomCode() {
        return customCode.getCustomCode();
    }

    @Override
    HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return customCode.getMessage();
    }
}
