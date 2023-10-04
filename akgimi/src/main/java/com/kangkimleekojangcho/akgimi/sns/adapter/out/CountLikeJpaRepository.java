package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.domain.CountLike;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountLikeJpaRepository extends JpaRepository<CountLike, Long> {
    Optional<CountLike> findByFeed(Feed feed);
    CountLike getReferenceByFeed(Feed feed);
}
