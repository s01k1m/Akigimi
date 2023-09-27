package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.UserField;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ActivateUserServiceResponse {
    private final String accessToken;
    private final String refreshToken;
    private final Long userId;

    public ActivateUserServiceResponse(String accessToken, String refreshToken, Long userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}
