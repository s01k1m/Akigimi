package com.kangkimleekojangcho.akgimi.global.response;

import lombok.Getter;

@Getter
public enum SuccessResponseMessage {
    message("요청에 성공하였습니다.");

    String value;

    SuccessResponseMessage(String value) {
        this.value = value;
    }
}
