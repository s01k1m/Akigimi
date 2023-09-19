package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChallengeJpaAdapter implements CommandChallengeDbPort, QueryChallengeDbPort {

    private final ChallengeJpaRepository challengeJpaRepository;

    @Override
    public Challenge save(Challenge challenge) {
        return challengeJpaRepository.save(challenge);
    }

    @Override
    public Optional<Challenge> findInProgressChallengeByUserId(Long userId) {
        return challengeJpaRepository.findChallengeByUserIdAndIsInProgress(userId, true);
    }
}
