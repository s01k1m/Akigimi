package com.example.demo.global.exception;

public abstract class BusinessException extends RuntimeException{
    abstract String getCustomCode();
}
