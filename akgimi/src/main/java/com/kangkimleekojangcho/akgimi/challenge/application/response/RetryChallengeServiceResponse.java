package com.kangkimleekojangcho.akgimi.challenge.application.response;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class RetryChallengeServiceResponse {
    private final Long challengeId;
    private final Long productId;
    private final Long accumulatedAmount;
    private final boolean achievementState;
    private final Integer challengePeriod;
    private final LocalDate challengeStartDate;
    private final LocalDate challengeEndDate;
    private final boolean isInProgress;

    public static RetryChallengeServiceResponse from(Challenge challenge){
        return RetryChallengeServiceResponse.builder()
                .challengeId(challenge.getId())
                .productId(challenge.getProduct().getId())
                .accumulatedAmount(challenge.getAccumulatedAmount())
                .achievementState(challenge.isAchievementState())
                .challengePeriod(challenge.getChallengePeriod())
                .challengeStartDate(challenge.getChallengeStartDate())
                .challengeEndDate(challenge.getChallengeEndDate())
                .isInProgress(challenge.isInProgress())
                .build();
    }
}
