package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.challenge.adapter.out.ChallengeJpaRepository;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengePort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.request.CreateChallengeServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreateChallengeServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateChallengeService{
    private final CommandChallengePort commandChallengePort;
    private final QueryUserDbPort queryUserDbPort;
    private final QueryProductDbPort queryProductDbPort;

    public CreateChallengeServiceResponse create(Long userId, CreateChallengeServiceRequest request) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        Product product = queryProductDbPort.findById(request.getItemId()).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_RESOURCE));
        Challenge challenge = Challenge.builder()
                .user(user)
                .product(product)
                //TODO: accumulatedAmount()
                .challengePeriod(request.getChallengePeriod())
                .challengeStartDate(LocalDate.now())
                .challengeEndDate(LocalDate.now().plusDays(request.getChallengePeriod()))
                .isInProgress(true)
                .build();
        Challenge save = commandChallengePort.create(challenge);
        return CreateChallengeServiceResponse.from(save);
    }
}
