package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.bank.application.CheckBalanceService;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.response.GetChallengeInProgressServiceResponse;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.challenge.domain.CharacterStatus;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetChallengeInProgressService {
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final QueryAccountDbPort queryAccountDbPort;

    public GetChallengeInProgressServiceResponse get(Long userId){
        Challenge challengeInProgress = queryChallengeDbPort.findInProgressChallengeByUserId(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));

        Account account = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.DEPOSIT, userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        Long balance = account.getBalance();

        int percentage = challengeInProgress.calculatePercentage(balance);
        int characterStatus = challengeInProgress.getCharacterStatusByPercentage(percentage).getLevel();
        int days = challengeInProgress.calculateDays(LocalDate.now());

        return GetChallengeInProgressServiceResponse.from(challengeInProgress, balance, percentage, characterStatus, days);
    }
}
