package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Getter;

@Getter
public class SignUpServiceResponse {
    private final Long userId;
    private final String accessToken;
    private final String refreshToken;

    public SignUpServiceResponse(Long userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
