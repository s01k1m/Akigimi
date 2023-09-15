package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.FeedCommandDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedCommandDbAdapter implements FeedCommandDbPort {

    private final FeedJpaRepository feedJpaRepository;
    @Override
    public void save(Feed feed) {
        feedJpaRepository.save(feed);
    }
}
