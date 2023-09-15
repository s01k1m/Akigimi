package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandChallengeJpaAdapter implements CommandChallengeDbPort {

    private final ChallengeJpaRepository challengeJpaRepository;

    @Override
    public Challenge save(Challenge challenge) {
        return challengeJpaRepository.save(challenge);
    }
}
