package com.kangkimleekojangcho.akgimi.ranking.application;

import com.kangkimleekojangcho.akgimi.bank.application.port.QueryAccountDbPort;
import com.kangkimleekojangcho.akgimi.bank.domain.Account;
import com.kangkimleekojangcho.akgimi.bank.domain.AccountType;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.ranking.application.response.GetAllRankingServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAllRankingService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryFollowDbPort queryFollowDbPort;
    private final QueryChallengeDbPort queryChallengeDbPort;
    private final QueryAccountDbPort queryAccountDbPort;

    public List<GetAllRankingServiceResponse> get(Long userId){
        List<GetAllRankingServiceResponse> ret = new ArrayList<>();
        User user = queryUserDbPort.findById(userId).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 팔로우 하고 있는 유저들을 가져온다.
        List<User> followees = queryFollowDbPort.getFollowee(user);
        for(User followee : followees){
            Optional<Challenge> challengeInProgress = queryChallengeDbPort.findInProgressChallengeByUserId(followee.getId());
            Account depositAccount = queryAccountDbPort.findByUserAndAccountType(followee, AccountType.DEPOSIT).orElseThrow(()-> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
            if (challengeInProgress.isPresent()){
                ret.add(
                        GetAllRankingServiceResponse.builder()
                                .userNickname(followee.getNickname())
                                .userImgUrl(followee.getProfileImageUrl())
                                .productName(challengeInProgress.get().getProduct().getName())
                                .percentage(challengeInProgress.get().calculatePercentage(depositAccount.getBalance()))
                                .build());
            }
        }
        Optional<Challenge> userChallengeInProgress = queryChallengeDbPort.findInProgressChallengeByUserId(user.getId());
        Account userDepositAccount = queryAccountDbPort.findByUserAndAccountType(user, AccountType.DEPOSIT).orElseThrow(()-> new BadRequestException(BadRequestExceptionCode.NO_BANK_ACCOUNT));
        ret.add(
                GetAllRankingServiceResponse.builder()
                        .userNickname(user.getNickname())
                        .userImgUrl(user.getProfileImageUrl())
                        .productName(userChallengeInProgress.get().getProduct().getName())
                        .percentage(userChallengeInProgress.get().calculatePercentage(userDepositAccount.getBalance()))
                        .build()
        );
        Collections.sort(ret);
        return ret;
    }
}
