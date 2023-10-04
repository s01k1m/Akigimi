package com.kangkimleekojangcho.akgimi.sns.application;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.*;
import com.kangkimleekojangcho.akgimi.sns.application.response.MarkLikeToFeedServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MarkLikeToFeedService {

    private final QueryFeedDbPort queryFeedDbPort;
    private final QueryUserDbPort queryUserDbPort;
    private final CommandLikeDbPort commandLikeDbPort;
    private final QueryCountLikeDbPort queryCountLikeDbPort;
    private final QueryLikeDbPort queryLikeDbPort;

    public MarkLikeToFeedServiceResponse execute(Long userId, Long feedId) {
        Feed feed = queryFeedDbPort.findReferenceById(feedId);
        User user = queryUserDbPort.findReferenceById(userId);

        //최적화 필요 -> queryDSL로 변경
        if(feed.getIsDeleted()) {
            throw new BadRequestException(BadRequestExceptionCode.NOT_FEED);
        }
        if(!feed.getIsPublic() && !feed.getUser().equals(user)) {
            throw new BadRequestException(BadRequestExceptionCode.NOT_FEED);
        }

        commandLikeDbPort.save(
                Like.builder()
                        .feed(feed)
                        .user(user)
                        .build());

        CountLike countLike = queryCountLikeDbPort.getReferenceByFeed(feed);
        countLike.setCount(queryLikeDbPort.countInFeed(feed));

        return MarkLikeToFeedServiceResponse
                .builder()
                .build();
    }
}
