package com.example.demo.global.exception;

import org.springframework.http.HttpStatus;

public abstract class HttpBusinessException extends BusinessException{
    abstract HttpStatus getHttpStatus();

}
