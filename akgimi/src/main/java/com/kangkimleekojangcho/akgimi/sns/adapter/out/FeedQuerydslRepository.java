package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.domain.QFeed;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class FeedQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QFeed feed = QFeed.feed;


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
                .where(ltFeedId(lastFeedId),
                        feed.user.id.eq(userId))
                .orderBy(feed.feedId.desc())
                .limit(numberOfFeed).fetch();
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
