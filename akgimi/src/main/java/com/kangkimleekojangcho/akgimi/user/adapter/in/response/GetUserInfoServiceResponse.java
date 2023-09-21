package com.kangkimleekojangcho.akgimi.user.adapter.in.response;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.Getter;


@Getter
public class GetUserInfoServiceResponse {
    private final Long userId;
    private final String nickname;
    private final String profileImageUrl;

    public GetUserInfoServiceResponse(User user) {
        userId = user.getId();
        nickname = user.getNickname();
        profileImageUrl = user.getProfileImageUrl();
    }
}
