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

        Integer productPrice = challengeInProgress.getProduct().getPrice();
        Account account = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.DEPOSIT, userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        Long balance = account.getBalance();
        Integer percentage = (int)Math.round(((double)balance/productPrice)*100);
        if (percentage > 100) percentage = 100;
        Integer characterStatus = getCharacterStatusByPercentage(percentage).getLevel();
        Integer days = challengeInProgress.getChallengeStartDate().until(LocalDate.now()).getDays()+1;
        return GetChallengeInProgressServiceResponse.from(challengeInProgress, balance, percentage, characterStatus, days);
    }

    public CharacterStatus getCharacterStatusByPercentage(Integer percentage){
        if (percentage < 20) return CharacterStatus.LEVEL0;
        if (percentage < 40) return CharacterStatus.LEVEL1;
        if (percentage < 60) return CharacterStatus.LEVEL2;
        if (percentage < 80) return CharacterStatus.LEVEL3;
        if (percentage < 100) return CharacterStatus.LEVEL4;
        return CharacterStatus.LEVEL5;
    }
}
