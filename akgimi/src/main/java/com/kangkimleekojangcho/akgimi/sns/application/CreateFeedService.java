package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.CommandTransferDbPort;
import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.bank.domain.Transfer;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandImagePort;
import com.kangkimleekojangcho.akgimi.sns.application.request.CreateFeedServiceRequest;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateFeedService {

    private final QueryUserDbPort queryUserDbPort;
    private final QueryAccountDbPort queryAccountDbPort;
    private final CommandFeedDbPort commandFeedDbPort;
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final CommandImagePort commandImagePort;
    private final CommandTransferDbPort commandTransferDbPort;
    private final CommandCountLikeDbPort commandCountLikeDbPort;

    @Transactional
    public Long createFeed(CreateFeedServiceRequest createFeedServiceRequest, Long userId) {
        User user = queryUserDbPort.findById(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));

        Challenge challenge = queryChallengeDbPort.findInProgressChallengeByUserId(userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));

        Account withdrawAccount = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.WITHDRAW, userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));

        withdrawAccount.withdraw(createFeedServiceRequest.saving());

        Account depositAccount = queryAccountDbPort.findAccountByAccountTypeAndUserId(AccountType.DEPOSIT, userId)
                .orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));

        String url = commandImagePort.save(createFeedServiceRequest.photo(), userId);

        Feed feed = commandFeedDbPort.save(createFeedServiceRequest.toEntity(depositAccount, user, challenge, url));

        commandCountLikeDbPort.save(
                CountLike.builder().likeCount(0L)
                        .feed(feed)
                        .build());

        depositAccount.deposit(createFeedServiceRequest.saving());

        commandTransferDbPort.save(Transfer.builder()
                .sendAccount(withdrawAccount)
                .sendAccountBalance(withdrawAccount.getBalance())
                .receiveAccount(depositAccount)
                .receiveAccountBalance(depositAccount.getBalance())
                .amount(createFeedServiceRequest.saving())
                .transferDateTime(LocalDateTime.now())
                .build());


        return feed.getFeedId();
    }
}
