package com.kangkimleekojangcho.akgimi.challenge.application.port;


import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetBunchOfPostscriptServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;

import java.util.List;
import java.util.Optional;

public interface QueryPostscriptDbPort {


    Optional<Postscript> findPostscriptByChallenge(Challenge challenge);

    GetBunchOfPostscriptServiceResponse findBunchOfPostscriptForProduct(GetBunchOfPostscriptServiceRequest request);
}
