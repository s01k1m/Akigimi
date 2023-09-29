package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandPostscriptDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryPostscriptDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.request.GetBunchOfPostscriptServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetBunchOfPostscriptServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostscriptJpaAdapter implements
        QueryPostscriptDbPort, CommandPostscriptDbPort {

    private final PostscriptJpaRepository postscriptJpaRepository;
    private final PostscriptQuerydslRepository postscriptQuerydslRepository;

    @Override
    public Postscript save(Postscript postscript) {
        return postscriptJpaRepository.save(postscript);
    }

    @Override
    public Optional<Postscript> findPostscriptByChallenge(Challenge challenge) {
        return postscriptJpaRepository.findPostscriptByChallenge(challenge);
    }

    @Override
    public GetBunchOfPostscriptServiceResponse findBunchOfPostscriptForProduct(
            GetBunchOfPostscriptServiceRequest request
    ) {
        if (request.numberOfPostscript() == 0) {
            return GetBunchOfPostscriptServiceResponse.builder()
                    .bunchOfPostscriptInfo(new ArrayList<>())
                    .build();
        }

        return GetBunchOfPostscriptServiceResponse.builder()
                .bunchOfPostscriptInfo(
                        postscriptQuerydslRepository.findBunchOfPostscriptForProduct(
                                request
                        )
                )
                .build();
    }
}
