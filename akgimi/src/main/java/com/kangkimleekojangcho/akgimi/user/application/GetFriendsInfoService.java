package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.application.response.FriendsServiceResponse;
import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFriendsInfoService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryFollowDbPort queryFollowDbPort;

    @Transactional(readOnly = true)
    public FriendsServiceResponse get(Long userId, String friendType) {
        User user = queryUserDbPort.findById(userId).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_USER));
        List<User> friends = new ArrayList<>();
        if(friendType.equals("FOLLOWING")){
            friends = queryFollowDbPort.getFollowingUser(user);
        }
        if(friendType.equals("FOLLOWED")){
            friends = queryFollowDbPort.getFollowedUser(user);
        }
        return FriendsServiceResponse.builder()
                .friends(friends)
                .build();
    }
}
