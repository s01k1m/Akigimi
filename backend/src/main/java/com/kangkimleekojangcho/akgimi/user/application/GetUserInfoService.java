package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.user.adapter.in.response.GetUserInfoServiceResponse;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryFollowDbPort;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.Follow;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {
    private final QueryUserDbPort queryUserDbPort;
    private final QueryFollowDbPort queryFollowDbPort;
    @Transactional(readOnly = true)
    public GetUserInfoServiceResponse get(Long id, Long userId) {
        User followee = queryUserDbPort.findById(id).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        User follower = queryUserDbPort.findById(userId).orElseThrow(() -> new BadRequestException(BadRequestExceptionCode.NOT_USER));
        List<Follow> isFollowed = queryFollowDbPort.isFollowed(followee, follower);
        if(isFollowed.size()>=1){
            return new GetUserInfoServiceResponse(followee, true);
        }else{
            return new GetUserInfoServiceResponse(followee, false);
        }
    }
}
