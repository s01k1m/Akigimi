package com.kangkimleekojangcho.akgimi.user.adapter.in.response;

import lombok.Getter;

@Getter
public class LoginUrlResponse {
    private final String loginUrl;
    private final String redirectUrl;

    public LoginUrlResponse(String loginUrl, String redirectUrl) {
        this.loginUrl = loginUrl;
        this.redirectUrl = redirectUrl;
    }
}
