package com.kangkimleekojangcho.akgimi.user.adapter.in.response;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.Builder;
import lombok.Getter;


@Getter
public class GetUserInfoServiceResponse {
    private final Long userId;
    private final String nickname;
    private final String profileImageUrl;
    private final Boolean isFollowed;

    public GetUserInfoServiceResponse(User user, Boolean isFollowed) {
        userId = user.getId();
        nickname = user.getNickname();
        profileImageUrl = user.getProfileImageUrl();
        this.isFollowed = isFollowed;
    }
}
