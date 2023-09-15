package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.challenge.application.request.CreateChallengeServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreateChallengeServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;

import java.util.Optional;

public interface CommandChallengePort {
    Challenge create(Challenge challenge);
}
