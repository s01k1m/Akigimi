package com.kangkimleekojangcho.akgimi.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotFoundExceptionCode {
    ;
    private final String code;
    private final String descriptionMessage;
}