package com.kangkimleekojangcho.akgimi.sns.application;


import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.response.CancelLikeServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import com.kangkimleekojangcho.akgimi.user.application.port.QueryUserDbPort;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CancelLikeService {

    private final QueryFeedDbPort queryFeedDbPort;
    private final QueryUserDbPort queryUserDbPort;
    private final CommandLikeDbPort commandLikeDbPort;
    private final QueryCountLikeDbPort queryCountLikeDbPort;
    private final QueryLikeDbPort queryLikeDbPort;

    public CancelLikeServiceResponse execute(Long userId, Long feedId) {
        Feed feed = queryFeedDbPort.findReferenceById(feedId);
        User user = queryUserDbPort.findReferenceById(userId);

        //최적화 필요 -> queryDSL로 변경
        if(feed.getIsDeleted()) {
            throw new BadRequestException(BadRequestExceptionCode.NOT_FEED);
        }

        if(!feed.getIsPublic() && !feed.getUser().equals(user)) {
            throw new BadRequestException(BadRequestExceptionCode.NOT_FEED);
        }

        //like를 했었는지 체크가 필요한가?
        //어차피 DB한번 쳐야됨
        //이건 굳이 고려하지 않아도 될 듯 하다.
        //음.. 로직이 조금 복잡해진다
        commandLikeDbPort.deleteByUserIdAndFeedId(user.getId(), feed.getFeedId());

        CountLike countLike = queryCountLikeDbPort.getReferenceByFeed(feed);

        countLike.setCount(queryLikeDbPort.countInFeed(feed));

        return CancelLikeServiceResponse
                .builder()
                .build();
    }
}
