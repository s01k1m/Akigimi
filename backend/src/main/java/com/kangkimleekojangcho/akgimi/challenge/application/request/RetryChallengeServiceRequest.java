package com.kangkimleekojangcho.akgimi.challenge.application.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetryChallengeServiceRequest {
    private int challengePeriod;
}
