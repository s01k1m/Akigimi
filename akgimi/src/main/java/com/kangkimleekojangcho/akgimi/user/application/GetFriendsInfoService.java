package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.FriendResponse;
import com.kangkimleekojangcho.akgimi.user.application.response.FriendsServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetFriendsInfoService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryFollowDbPort queryFollowDbPort;
    private final QueryChallengeDbPort queryChallengeDbPort;

    @Transactional(readOnly = true)
    public FriendsServiceResponse get(Long userId, String friendType) {
        User user = queryUserDbPort.findById(userId).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_USER));
        List<User> friends = new ArrayList<>();
        if(friendType.equals("FOLLOWING")){
            friends = queryFollowDbPort.getFollower(user);
        } else if(friendType.equals("FOLLOWED")){
            friends = queryFollowDbPort.getFollowee(user);
        } else{
            throw new BadRequestException(BadRequestExceptionCode.NO_AVAILABLE_FRIEND_TYPE);
        }
        List<FriendResponse> response = new ArrayList<>();
        for(User friend : friends){
            // friend 도전중인 챌린지 정보를 가져온다.
            Optional<Challenge> challenge = queryChallengeDbPort.findInProgressChallengeByUserId(friend.getId());
            if(challenge.isPresent()){ // 도전중인 챌린지가 있으면
                response.add(FriendResponse.builder()
                        .id(friend.getId())
                        .nickname(friend.getNickname())
                        .profileImageUrl(friend.getProfileImageUrl())
                        .challengeId(challenge.get().getId())
                        .productId(challenge.get().getProduct().getId())
                        .accumulatedAmount(challenge.get().getAccumulatedAmount())
                        .build());
            }else{ // 도전중인 챌린지가 없으면
                response.add(FriendResponse.builder()
                        .id(friend.getId())
                        .nickname(friend.getNickname())
                        .profileImageUrl(friend.getProfileImageUrl())
                        .build());
            }

        }
        return FriendsServiceResponse.builder()
                .friends(response)
                .build();
    }
}
