package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import lombok.Getter;

@Getter
public class DevelopLoginServiceResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String userState;
    private final Long userId;

    public DevelopLoginServiceResponse(String accessToken, String refreshToken, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userState = user.getUserState().name();
        this.userId = user.getId();
    }
}
