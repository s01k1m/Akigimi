package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Getter;

@Getter
public class DevelopLoginServiceResponse {
    private final String accessToken;
    private final String refreshToken;

    public DevelopLoginServiceResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
