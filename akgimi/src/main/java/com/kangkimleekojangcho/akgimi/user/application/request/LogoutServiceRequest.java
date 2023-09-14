package com.kangkimleekojangcho.akgimi.user.application.request;


import lombok.Getter;

@Getter
public class LogoutServiceRequest {
    private final String accessToken;
    private final String refreshToken;

    public LogoutServiceRequest(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
