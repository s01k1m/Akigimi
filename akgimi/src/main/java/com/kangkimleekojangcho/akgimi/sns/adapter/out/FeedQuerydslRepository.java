package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.domain.QFeed;
import com.kangkimleekojangcho.akgimi.user.domain.QFollow;
import com.kangkimleekojangcho.akgimi.user.domain.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.kangkimleekojangcho.akgimi.sns.domain.QFeed.*;
import static com.kangkimleekojangcho.akgimi.user.domain.QFollow.follow;
import static com.kangkimleekojangcho.akgimi.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class FeedQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<BriefFeedInfo> findByUser_IdAndLastFeedIdAndNumberOfFeed(
            Long userId,
            Long lastFeedId,
            Integer numberOfFeed
    ) {

        List<BriefFeedInfo> result = jpaQueryFactory.select(
                        new QBriefFeedInfo(
                                feed.user.id.as("userId"),
                                feed.user.nickname.as("nickname"),
//                                feed.user.profile.as("userProfile"), TODO: profile 나중에 만들것
                                feed.price.as("price"),
//                                feed.like.as("likes"), TODO: like 수 계산해서 가져와야 함.
                                feed.notPurchasedItem.as("notPurchasedItem"),
                                feed.place.as("akgimiPlace"),
                                feed.content.as("content"),
//                                feed..as("isLikedFeed"), TODO: 이것도 확인해서 가져와야 함.
                                feed.imageUrl.as("photo")
                        )
                )
                .from(feed)
                //TODO: 팔로우 기능 만들어지면 팔로우된 유저만 처리하도록 해주기.
//                .join(follow.follower, user)
                //묵시적 조인은 허용하지 않는다.
                //Cross 조인에 의해 모든 행이 카타시안 곱으로 나타남.
                //그러면 이걸 join으로 걸어줘야 하는데 어떻게 걸것인가
                //1.
                .where(ltFeedId(lastFeedId), //최신순으로부터 가져온다.
                        feed.user.id.eq(userId)
                        .or(feed.isPublic.eq(true))//조건 1. feed의 userId가 요청자와 동일해야 한다.
                )
                .orderBy(feed.feedId.desc())
                .limit(numberOfFeed).fetch();

                //조건 2. feed의 userId가 팔로워와 동일해야 한다. + 팔로워의 사이즈와 동일해야 한다.
        //      select * from follow where follower=userId
                //조건 3. 활성화된 유저의 것만 보여주어야 한다.
        return result;
    }

    public List<BriefReceiptInfo> findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(
            Long requestUserId, Long receiptOwnerId,
            Long lastReceiptId, Integer numberOfReceipt
    ) {
        List<BriefReceiptInfo> result = jpaQueryFactory.select(
                        new QBriefReceiptInfo(
                                feed.price.as("price"),
                                feed.notPurchasedItem.as("notPurchasedItem"),
                                feed.place.as("akgimiPlace"),
                                feed.createdAt.as("createdDateTime"),
                                feed.imageUrl.as("photo")
                        )
                )
                .from(feed)
                .where(ltFeedId(lastReceiptId),
                        feed.user.id.eq(requestUserId),isMine(requestUserId, receiptOwnerId))
                .orderBy(feed.feedId.desc())
                .limit(numberOfReceipt).fetch();
        return result;
    }

    private BooleanExpression isMine(Long userId, Long receiptOwnerId) {
        if(Objects.equals(userId, receiptOwnerId)) {
            return null; //null 반환되면 조건문 자동 제거
        }
        return feed.isPublic.eq(true);
    }

    private BooleanExpression ltFeedId(Long feedId) {
        if (feedId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return feed.feedId.lt(feedId);
    }
}
