package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.response.MarkLikeToFeedServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MarkLikeToFeedService {

    private final QueryFeedDbPort queryFeedDbPort;
    private final QueryUserDbPort queryUserDbPort;
    private final CommandLikeDbPort commandLikeDbPort;


    public MarkLikeToFeedServiceResponse execute(Long userId, Long feedId) {
        //TODO : exception handling 뺄 방법 생각해보기
        try {
            Feed feed = queryFeedDbPort.findReferenceById(feedId);
            User user = queryUserDbPort.findReferenceById(userId);
            commandLikeDbPort.save(
                    Like.builder()
                            .feed(feed)
                            .user(user)
                            .build()
            );
        } catch (DataAccessException ex) {
            throw new BadRequestException(BadRequestExceptionCode.INVALID_INPUT);
        }

        return MarkLikeToFeedServiceResponse
                .builder()
                .build();
    }
}
