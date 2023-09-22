package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.Getter;

@Getter
public class LoginServiceResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String userState;

    public LoginServiceResponse(String accessToken, String refreshToken, UserState userState) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userState = userState.name();
    }
}
