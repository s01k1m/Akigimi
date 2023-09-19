package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;

import com.kangkimleekojangcho.akgimi.challenge.application.request.CreateChallengeServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class CreateChallengeRequest {
    @Positive(message = "챌린지 기간을 정확히 입력해주세요.")
    private Integer challengePeriod;
    private Long itemId;

    public CreateChallengeServiceRequest toServiceRequest() {
        return new CreateChallengeServiceRequest(challengePeriod, itemId);
    }
}
