package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Getter;

@Getter
public class SignUpServiceResponse {
    private final String accessToken;
    private final String refreshToken;

    public SignUpServiceResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
