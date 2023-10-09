package com.kangkimleekojangcho.akgimi.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FailResponse {
    private final String message;
    private final String code;
}
