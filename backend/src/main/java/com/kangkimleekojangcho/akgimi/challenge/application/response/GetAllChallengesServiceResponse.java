package com.kangkimleekojangcho.akgimi.challenge.application.response;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class GetAllChallengesServiceResponse {
    private final Long challengeId;
    private final Long productId;
    private final boolean achievementState;
    private final Integer challengePeriod;
    private final LocalDate challengeStartDate;
    private final LocalDate challengeEndDate;
    private final Integer challengeDays;
    private final boolean isInProgress;
    private final boolean isPostscriptCreated;

    public static GetAllChallengesServiceResponse from(Challenge challenge){
        int days;
        if(challenge.isInProgress()){
            days = challenge.calculateDays(LocalDate.now());
        }else{
            days = challenge.calculateDays(challenge.getAchievementDate());
        }
        return GetAllChallengesServiceResponse.builder()
                .challengeId(challenge.getId())
                .productId(challenge.getProduct().getId())
                .achievementState(challenge.isAchievementState())
                .challengePeriod(challenge.getChallengePeriod())
                .challengeStartDate(challenge.getChallengeStartDate())
                .challengeEndDate(challenge.getChallengeEndDate())
                .challengeDays(days)
                .isInProgress(challenge.isInProgress())
                //TODO: .isPostscriptCreated()
                .build();
    }
}
