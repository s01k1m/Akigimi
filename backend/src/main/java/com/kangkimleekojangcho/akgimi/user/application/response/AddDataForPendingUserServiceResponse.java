package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Getter;

@Getter
public class AddDataForPendingUserServiceResponse {
    private final String accessToken;
    private final String refreshToken;

    public AddDataForPendingUserServiceResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
