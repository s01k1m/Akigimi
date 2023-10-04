package com.kangkimleekojangcho.akgimi.sns.application.port;

import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;

import java.util.Optional;

public interface QueryLikeDbPort {

    Optional<Like> findByUserIdAndFeedId(Long userId, Long feedId);

    Long countInFeed(Feed feedId);
}
