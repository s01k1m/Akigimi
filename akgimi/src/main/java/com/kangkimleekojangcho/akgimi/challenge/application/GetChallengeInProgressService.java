package com.kangkimleekojangcho.akgimi.challenge.application;

import com.kangkimleekojangcho.akgimi.bank.application.CheckBalanceService;
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
    private final CheckBalanceService checkBalanceService;

    public GetChallengeInProgressServiceResponse get(Long userId){
        Challenge challengeInProgress = queryChallengeDbPort.findInProgressChallengeByUserId(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));

        Integer productPrice = challengeInProgress.getProduct().getPrice();
        //TODO : Bank 연결
        //Long balance = checkBalanceService.checkBalance(userId, AccountType.DEPOSIT.toString()).getBalance();
        Integer balance = 2000;
        Integer percentage = (int)Math.round(((double)balance/productPrice)*100);
        Integer characterStatus = getCharacterStatusByPercentage(percentage).getLevel();
        Integer days = challengeInProgress.getChallengeStartDate().until(LocalDate.now()).getDays()+1;
        return GetChallengeInProgressServiceResponse.from(challengeInProgress, percentage, characterStatus, days);
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
