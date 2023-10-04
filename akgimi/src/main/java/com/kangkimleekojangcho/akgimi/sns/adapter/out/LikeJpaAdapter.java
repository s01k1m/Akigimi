package com.kangkimleekojangcho.akgimi.sns.adapter.out;

import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.sns.application.port.CommandLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.application.port.QueryLikeDbPort;
import com.kangkimleekojangcho.akgimi.sns.domain.Feed;
import com.kangkimleekojangcho.akgimi.sns.domain.Like;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class LikeJpaAdapter implements CommandLikeDbPort, QueryLikeDbPort {
    private final LikeJpaRepository likeJpaRepository;

    @Override
    public Like save(Like like) {
        try {
            return likeJpaRepository.save(like);
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(BadRequestExceptionCode.DUPLICATE_LIKE_REQUEST);
        }
    }

    @Override
    public Optional<Like> findByUserIdAndFeedId(Long userId, Long feedId) {
        return likeJpaRepository.findByUser_idAndFeed_feedId(userId, feedId);
    }

    @Override
    public void deleteByUserIdAndFeedId(Long id, Long feedId) {
        likeJpaRepository.deleteByUser_idAndFeed_feedId(id, feedId);
    }

    @Override
    public Long countInFeed(Feed feed) {
        return likeJpaRepository.countByFeed(feed);
    }
}
