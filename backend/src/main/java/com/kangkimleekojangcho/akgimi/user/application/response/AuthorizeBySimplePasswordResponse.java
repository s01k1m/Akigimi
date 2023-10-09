package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Getter;

@Getter
public class AuthorizeBySimplePasswordResponse {
    private final Boolean isPasswordCorrect;
    private final String accessToken;
    private final String userState;
    private final Long userId;

    public AuthorizeBySimplePasswordResponse(Boolean isPasswordCorrect, String accessToken, String userState, Long userId) {
        this.isPasswordCorrect = isPasswordCorrect;
        this.accessToken = accessToken;
        this.userState = userState;
        this.userId = userId;
    }
}
