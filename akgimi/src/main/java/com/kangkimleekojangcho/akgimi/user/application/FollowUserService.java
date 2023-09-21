package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowUserService {
    private final QueryUserDbPort queryUserDbPort;

    @Transactional
    public boolean followUser(long userId, long followUser) {
        // 1. follower 유저 정보를 가져온다.
        User follower = queryUserDbPort.findById(userId).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 2. followee 유저 정보를 가져온다.
        User followee =queryUserDbPort.findById(followUser).orElseThrow(()->new BadRequestException(BadRequestExceptionCode.NOT_USER));
        // 3. Follow
        Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .build();
        return true;
    }
}
