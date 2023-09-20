package com.kangkimleekojangcho.akgimi.challenge.application.response;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.CharacterStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetChallengeInProgressServiceResponse {
    private Long balance;
    private Integer percentage;
    private Integer characterStatus;
    private String productName;
    private Integer productPrice;
    private String productImgUrl;
    private Integer challengePeriod;
    private Integer days;

    public static GetChallengeInProgressServiceResponse from(Challenge challenge, Long balance, Integer percentage, Integer characterStatus, Integer days){
        return GetChallengeInProgressServiceResponse.builder()
                .balance(balance)
                .percentage(percentage)
                .characterStatus(characterStatus)
                .productName(challenge.getProduct().getName())
                .productPrice(challenge.getProduct().getPrice())
                .productImgUrl(challenge.getProduct().getUrl())
                .challengePeriod(challenge.getChallengePeriod())
                .days(days)
                .build();
    }
}
