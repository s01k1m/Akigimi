package com.kangkimleekojangcho.akgimi.sns.application.port;

import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;

import java.util.Optional;

public interface QueryCountLikeDbPort {
    Optional<CountLike> findByFeed(Feed feed);
    CountLike getReferenceByFeed(Feed feed);
}
