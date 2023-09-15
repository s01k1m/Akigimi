package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QueryFeedJpaAdapter implements QueryFeedDbPort {

    private final FeedJpaRepository feedJpaRepository;

    @Override
    public Optional<Feed> findById(Long feedId) {
        return feedJpaRepository.findById(feedId);
    }
}
