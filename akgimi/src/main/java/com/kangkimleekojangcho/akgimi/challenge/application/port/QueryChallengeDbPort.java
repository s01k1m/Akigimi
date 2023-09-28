package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;

import java.util.List;
import java.util.Optional;

public interface QueryChallengeDbPort {

    Optional<Challenge> findInProgressChallengeByUserId(Long userId);
    List<Challenge> findAllByUserId(Long userId);
    Integer countByProductIdAndIsInProgress(Long productId, boolean isInProgress);

    Optional<Challenge> findSuccessChallengeByIdAndUser_Id(Long userId, Long challengeId);
}
