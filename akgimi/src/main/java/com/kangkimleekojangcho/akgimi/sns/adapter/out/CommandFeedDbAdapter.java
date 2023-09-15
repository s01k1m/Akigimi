package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.CommandFeedDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandFeedDbAdapter implements CommandFeedDbPort {

    private final FeedJpaRepository feedJpaRepository;
    @Override
    public Feed save(Feed feed) {
        return feedJpaRepository.save(feed);
    }
}
