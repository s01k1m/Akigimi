package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Getter;

@Getter
public class LoginServiceResponse {
    private final String accessToken;
    private final String refreshToken;

    public LoginServiceResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
