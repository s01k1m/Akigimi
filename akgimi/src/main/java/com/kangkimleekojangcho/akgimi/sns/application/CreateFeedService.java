package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.sns.application.port.FeedCommandDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.request.CreateFeedServiceRequest;
import com.kangkimleekojangcho.akgimi.user.application.port.CommandUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateFeedService {

    private final CommandUserDbPort commandUserDbPort;
    private final FeedCommandDbPort feedCommandDbPort;

    public void createFeed(CreateFeedServiceRequest createFeedServiceRequest, Long userId) {
        //1. 유저 확인하기
        User user = commandUserDbPort
        //2. 챌린지 기록 가져오기

        //3. 통장 계좌 확인.

        //4. 저장

        //5. 마지막으로 통장 계좌에 저장
        feedCommandDbPort.insert();
    }
}
