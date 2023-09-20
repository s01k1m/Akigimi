package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;

public interface CommandChallengeDbPort {
    Challenge save(Challenge challenge);
}
