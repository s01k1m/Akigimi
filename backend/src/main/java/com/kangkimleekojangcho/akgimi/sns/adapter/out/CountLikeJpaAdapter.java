package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.application.port.CommandCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryCountLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CountLikeJpaAdapter implements CommandCountLikeDbPort, QueryCountLikeDbPort {

    private final CountLikeJpaRepository countLikeJpaRepository;

    @Override
    public CountLike save(CountLike countLike) {
        return countLikeJpaRepository.save(countLike);
    }

    @Override
    public CountLike getReferenceByFeed(Feed feed) {
        return countLikeJpaRepository.getReferenceByFeed(feed);
    }

    @Override
    public Optional<CountLike> findByFeed(Feed feed) {
        return countLikeJpaRepository.findByFeed(feed);
    }
}
