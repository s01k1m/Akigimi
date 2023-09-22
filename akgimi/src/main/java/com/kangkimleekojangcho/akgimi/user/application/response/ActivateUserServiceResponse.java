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
    private final List<String> userFields;

    public ActivateUserServiceResponse(String accessToken, String refreshToken, Long userId, List<UserField> userFields) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.userFields = userFields.stream().map(UserField::name).toList();
    }
}
