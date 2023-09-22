package com.kangkimleekojangcho.akgimi.user.application.response;

import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class FriendsServiceResponse {
    private final List<FriendResponse> friends;
}
