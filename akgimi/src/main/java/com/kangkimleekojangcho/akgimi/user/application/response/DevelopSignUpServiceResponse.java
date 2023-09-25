package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.Getter;

@Getter
public class DevelopSignUpServiceResponse {
    private final Long userId;
    private final String accessToken;
    private final String refreshToken;

    private DevelopSignUpServiceResponse(Long userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static DevelopSignUpServiceResponse of(User user,
                                                  String accessToken,
                                                  String refreshToken){
        return new DevelopSignUpServiceResponse(
                user.getId(),
                accessToken,
                refreshToken
        );
    }
}
