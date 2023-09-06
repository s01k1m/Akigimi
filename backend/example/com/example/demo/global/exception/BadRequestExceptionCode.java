package com.example.demo.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BadRequestExceptionCode {
    NAME_NOT_FOUND("이름이 발견되지 않았습니다.","001"),
    USER_NOT_FOUND("유저가 발견되지 않았습니다.", "002"),
    NEGATIVE_MONEY("금액이 음수값입니다.", "003"),;
    private final String message;
    private final String customCode;
}
