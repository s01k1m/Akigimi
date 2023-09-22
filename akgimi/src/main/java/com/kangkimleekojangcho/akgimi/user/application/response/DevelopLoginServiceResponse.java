package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.Getter;

@Getter
public class DevelopLoginServiceResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String userState;

    public DevelopLoginServiceResponse(String accessToken, String refreshToken, UserState userState) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userState = userState.name();
    }
}
