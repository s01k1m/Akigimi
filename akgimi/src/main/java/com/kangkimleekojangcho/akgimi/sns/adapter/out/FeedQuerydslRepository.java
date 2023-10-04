package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.BriefReceiptInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefReceiptInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.kangkimleekojangcho.akgimi.sns.domain.QFeed.feed;
import static com.kangkimleekojangcho.akgimi.sns.domain.QLike.like;
import static com.kangkimleekojangcho.akgimi.user.domain.QFollow.follow;
import static com.kangkimleekojangcho.akgimi.user.domain.QUser.user;

@Log4j2
@Repository
@RequiredArgsConstructor
public class FeedQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<BriefFeedInfo> findByUser_IdAndLastFeedIdAndNumberOfFeed(
            Long requestUserId,
            Long lastFeedId,
            Integer numberOfFeed
    ) {

        return jpaQueryFactory.select(
                        new QBriefFeedInfo(
                                feed.feedId.as("feedId"),
                                feed.user.id.as("userId"),
                                feed.user.profileImageUrl.as("userProfile"),
                                feed.user.nickname.as("nickname"),
                                feed.price.as("price"),
                                feed.notPurchasedItem.as("notPurchasedItem"),
                                feed.place.as("akgimiPlace"),
                                feed.content.as("content"),
                                feed.like.size().eq(1),
                                feed.imageUrl.as("photo")
                        )
                ).distinct()
                .from(feed)
                .join(feed.user, user)
                .leftJoin(follow)
                .on(
                        feed.user.id.eq(requestUserId).or( //여기가 문제
                                feed.user.eq(follow.followee).and(
                                        follow.follower.id.eq(requestUserId)
                                                .and(follow.followee.eq(feed.user))
                                                .and(feed.isPublic.eq(true))
                                )
                        )
                )
                .leftJoin(like)
                .on(feed.eq(like.feed)
                        .and(like.user.id.eq(requestUserId)))
                .where(ltFeedId(lastFeedId),
                        feed.user.id.eq(requestUserId).or(
                                follow.follower.id.eq(requestUserId)))
                .orderBy(feed.feedId.desc())
                .limit(numberOfFeed).fetch();
    }

    public List<BriefReceiptInfo> findReceiptByUser_IdAndLastReceiptIdAndNumberOfReceipt(
            Long requestUserId, Long receiptOwnerId,
            Long lastReceiptId, Integer numberOfReceipt
    ) {
        List<BriefReceiptInfo> result = jpaQueryFactory.select(
                        new QBriefReceiptInfo(
                                feed.feedId.as("receiptId"),
                                feed.price.as("price"),
                                feed.notPurchasedItem.as("notPurchasedItem"),
                                feed.place.as("akgimiPlace"),
                                feed.createdAt.as("createdDateTime"),
                                feed.imageUrl.as("photo")
                        )
                )
                .from(feed)
                .where(ltFeedId(lastReceiptId),
                        feed.user.id.eq(requestUserId), isMine(requestUserId, receiptOwnerId))
                .orderBy(feed.feedId.desc())
                .limit(numberOfReceipt).fetch();
        return result;
    }

    private BooleanExpression isMine(Long userId, Long receiptOwnerId) {
        if (Objects.equals(userId, receiptOwnerId)) {
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
