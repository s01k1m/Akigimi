package com.kangkimleekojangcho.akgimi.sns.application.port;

import com.kangkimleekojangcho.akgimi.sns.domain.Feed;

import java.util.Optional;

public interface QueryFeedDbPort {

    Optional<Feed> findById(Long feedId);
}
