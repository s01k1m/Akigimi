package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengePort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeJpaAdapter implements CommandChallengePort {
    private final ChallengeJpaRepository challengeJpaRepository;
    @Override
    public Challenge create(Challenge challenge) {
        return challengeJpaRepository.save(challenge);
    }
}
