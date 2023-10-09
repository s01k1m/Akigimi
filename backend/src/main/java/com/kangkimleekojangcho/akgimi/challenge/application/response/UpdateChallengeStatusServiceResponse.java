package com.kangkimleekojangcho.akgimi.challenge.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateChallengeStatusServiceResponse {
    private final String productUrl;
}
