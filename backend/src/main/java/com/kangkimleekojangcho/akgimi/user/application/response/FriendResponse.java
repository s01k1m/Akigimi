package com.kangkimleekojangcho.akgimi.user.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class FriendResponse {
    private final Long id;
    private final String nickname;
    private final String profileImageUrl;
    private final Long productId;
    private final Long challengeId;
    private final Long accumulatedAmount;

}
