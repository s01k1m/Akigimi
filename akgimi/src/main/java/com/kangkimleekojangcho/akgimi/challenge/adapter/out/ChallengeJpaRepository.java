package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeJpaRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findChallengeByUserIdAndIsInProgress(Long userId, boolean isInProgress);
    List<Challenge> findAllByUserId(Long userId);
    Integer countByProductIdAndIsInProgress(Long productId, boolean isInProgress);
}
