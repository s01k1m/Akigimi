package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;

import com.kangkimleekojangcho.akgimi.challenge.application.request.RetryChallengeServiceRequest;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RetryChallengeRequest {
    @Positive(message = "챌린지 기간을 정확히 입력해주세요.")
    private int challengePeriod;

    public RetryChallengeServiceRequest toServiceRequest(){
        return RetryChallengeServiceRequest.builder()
                .challengePeriod(challengePeriod)
                .build();
    }

}
