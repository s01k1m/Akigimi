package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeJpaRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findChallengeByUserIdAndIsInProgress(Long userId, boolean isInProgress);
}
