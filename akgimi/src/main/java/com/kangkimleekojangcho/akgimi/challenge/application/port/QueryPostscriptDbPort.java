package com.kangkimleekojangcho.akgimi.challenge.application.port;


import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;

import java.util.Optional;

public interface QueryPostscriptDbPort {


    Optional<Postscript> findPostscriptByChallenge(Challenge challenge);
}
