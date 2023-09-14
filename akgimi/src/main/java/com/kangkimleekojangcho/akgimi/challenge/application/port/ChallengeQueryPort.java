package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ChallengeQueryPort {

    Optional<Challenge> findInProgressChallengeByUserId(Long userId);
}
