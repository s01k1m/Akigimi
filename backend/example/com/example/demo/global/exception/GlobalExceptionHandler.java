package com.example.demo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpBusinessException.class)
    public ResponseEntity<String> httpBusinessException(HttpBusinessException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        String customCode = e.getCustomCode();
        String message = e.getMessage();
        String clientMessage = String.format("custom code : %s | message: %s", customCode, message);
        return new ResponseEntity<>(clientMessage, httpStatus);
    }
}
