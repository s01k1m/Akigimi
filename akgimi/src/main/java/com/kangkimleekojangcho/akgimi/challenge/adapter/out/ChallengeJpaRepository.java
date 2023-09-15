package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeJpaRepository extends JpaRepository<Challenge, Long> {
}
