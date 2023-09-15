package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueryChallengeJpaAdapter implements QueryChallengeDbPort {

    private final ChallengeJpaRepository challengeJpaRepository;
    @Override
    public Optional<Challenge> findInProgressChallengeByUserId(Long userId) {
        return challengeJpaRepository.findChallengeByUserIdAndIsInProgress(userId, true);
    }
}
