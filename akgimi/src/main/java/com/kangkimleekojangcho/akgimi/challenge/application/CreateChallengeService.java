package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.request.CreateChallengeServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreateChallengeServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.product.application.port.QueryProductDbPort;
import com.kangkimleekojangcho.akgimi.product.domain.Product;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateChallengeService{
    private final CommandChallengeDbPort commandChallengePort;
    private final QueryUserDbPort queryUserDbPort;
    private final QueryProductDbPort queryProductDbPort;
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final QueryAccountDbPort queryAccountDbPort;

    public CreateChallengeServiceResponse create(Long userId, CreateChallengeServiceRequest request) {
        User user = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        queryChallengeDbPort.findInProgressChallengeByUserId(userId).ifPresent(challenge -> {
            throw new BadRequestException(BadRequestExceptionCode.ALREADY_PARTICIPATE_IN_CHALLENGE);});
        Product product = queryProductDbPort.findById(request.getItemId()).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_PRODUCT));
        Account depositAccount = queryAccountDbPort.findByUserAndAccountType(user, AccountType.DEPOSIT).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));

        Challenge challenge = Challenge.builder()
                .user(user)
                .product(product)
                .accumulatedAmount(depositAccount.getBalance())
                .challengePeriod(request.getChallengePeriod())
                .challengeStartDate(LocalDate.now())
                .challengeEndDate(LocalDate.now().plusDays(request.getChallengePeriod()))
                .isInProgress(true)
                .build();
        Challenge save = commandChallengePort.save(challenge);
        return CreateChallengeServiceResponse.from(save);
    }
}