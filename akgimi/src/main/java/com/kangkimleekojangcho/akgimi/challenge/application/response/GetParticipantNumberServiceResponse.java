package com.kangkimleekojangcho.akgimi.challenge.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetParticipantNumberServiceResponse {
    private Integer count;

    public static GetParticipantNumberServiceResponse from(Integer count){
        return GetParticipantNumberServiceResponse.builder()
                .count(count)
                .build();
    }
}
