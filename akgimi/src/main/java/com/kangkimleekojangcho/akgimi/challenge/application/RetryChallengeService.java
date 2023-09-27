package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.request.RetryChallengeServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.RetryChallengeServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RetryChallengeService {
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final CommandChallengeDbPort commandChallengeDbPort;
    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;

    public RetryChallengeServiceResponse create(Long userId, RetryChallengeServiceRequest request){
        User user = queryUserDbPort.findById(userId).orElseThrow(()-> new BadRequestException(BadRequestExceptionCode.NOT_USER));

        Account account = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.DEPOSIT, userId).orElseThrow(()-> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));

        Challenge challenge = queryChallengeDbPort.findInProgressChallengeByUserId(userId).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));

        //기존 챌린지 정보 업데이트
        challenge.update(null, null, false);

        Long balance = account.getBalance();
        Challenge save = commandChallengeDbPort.save(
                Challenge.builder()
                        .user(user)
                        .product(challenge.getProduct())
                        .accumulatedAmount(balance)
                        .achievementState(false)
                        .challengePeriod(request.getChallengePeriod())
                        .challengeStartDate(LocalDate.now())
                        .isInProgress(true)
                        .build()
        );

        return RetryChallengeServiceResponse.from(save);
    }
}
