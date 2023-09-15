package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;

import com.kangkimleekojangcho.akgimi.challenge.application.request.CreateChallengeServiceRequest;
import lombok.Getter;

@Getter
public class CreateChallengeRequest {
    private Integer challengePeriod;
    private Long itemId;

    public CreateChallengeServiceRequest toServiceRequest() {
        return new CreateChallengeServiceRequest(challengePeriod, itemId);
    }
}