package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeJpaRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUser_idAndFeed_feedId(Long userId, Long feedId);
    void deleteByUser_idAndFeed_feedId(Long id, Long feedId);

    Long countByFeed(Feed feed);
}
