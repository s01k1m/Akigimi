package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorException;
import com.kangkimleekojangcho.akgimi.global.exception.ServerErrorExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedImagePort;
import com.kangkimleekojangcho.akgimi.sns.application.request.CreateFeedServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateFeedService {

    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final CommandFeedDbPort commandFeedDbPort;
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final CommandFeedImagePort commandFeedImagePort;

    public Long createFeed(CreateFeedServiceRequest createFeedServiceRequest, Long userId) {

        //1. 유저 확인하기
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));

        //2. 챌린지 기록 가져오기
        Challenge challenge = queryChallengeDbPort.findInProgressChallengeByUserId(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));

        //3. 통장 계좌 확인.
        Account withdrawAccount = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.WITHDRAW, userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        withdrawAccount.withdraw(createFeedServiceRequest.saving());
        Account depositAccount = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.DEPOSIT, userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        String url = commandFeedImagePort.save(createFeedServiceRequest.photo(), userId)
                .orElseThrow(() -> new ServerErrorException(ServerErrorExceptionCode.NETWORK_ERROR));
        //feedback 저장
        Feed feed = commandFeedDbPort.save(createFeedServiceRequest.toEntity(depositAccount, user, challenge, url));

        //5. 마지막으로 통장 계좌에 저장
        depositAccount.deposit(createFeedServiceRequest.saving());
        return feed.getFeedId();
    }
}
