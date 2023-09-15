package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeedJpaAdapter implements CommandFeedDbPort, QueryFeedDbPort {

    private final FeedJpaRepository feedJpaRepository;
    @Override
    public Feed save(Feed feed) {
        return feedJpaRepository.save(feed);
    }

    @Override
    public Optional<Feed> findById(Long feedId) {
        return feedJpaRepository.findById(feedId);
    }
}
