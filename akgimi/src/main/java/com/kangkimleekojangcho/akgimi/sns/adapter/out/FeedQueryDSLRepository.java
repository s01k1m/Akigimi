package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.response.BriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.application.response.QBriefFeedInfo;
import com.kangkimleekojangcho.akgimi.sns.domain.QFeed;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QFeed feed = QFeed.feed; //아직 미심쩍음. 되는지 안되는지 모르겠음

    public List<BriefFeedInfo> findByUser_IdAndLastFeedIdAndNumberOfFeed(
            Long userId, Long lastFeedId, Integer numberOfFeed) {

        List<BriefFeedInfo> result = jpaQueryFactory.select(
                        new QBriefFeedInfo(
                                feed.user.id.as("userId"),
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

    private BooleanExpression ltFeedId(Long feedId) {
        if (feedId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return feed.feedId.lt(feedId);
    }
}
