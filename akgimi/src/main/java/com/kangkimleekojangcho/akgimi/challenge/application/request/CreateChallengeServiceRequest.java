package com.kangkimleekojangcho.akgimi.challenge.application.request;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreateChallengeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChallengeServiceRequest {
    private Integer challengePeriod;
    private Long itemId;

    public static CreateChallengeServiceRequest from(CreateChallengeRequest request){
        return CreateChallengeServiceRequest.builder()
                .challengePeriod(request.getChallengePeriod())
                .itemId(request.getItemId())
                .build();
    }
}

