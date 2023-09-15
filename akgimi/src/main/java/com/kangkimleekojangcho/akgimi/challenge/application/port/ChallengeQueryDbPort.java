package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;

import java.util.Optional;

public interface ChallengeQueryDbPort {

    Optional<Challenge> findInProgressChallengeByUserId(Long userId);
}
