package com.kangkimleekojangcho.akgimi.challenge.adapter.out;

import com.kangkimleekojangcho.akgimi.challenge.application.port.CommandChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.application.port.QueryChallengeDbPort;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChallengeJpaAdapter implements CommandChallengeDbPort, QueryChallengeDbPort {

    private final ChallengeJpaRepository challengeJpaRepository;

    @Override
    public Challenge save(Challenge challenge) {
        return challengeJpaRepository.save(challenge);
    }

    @Override
    public Optional<Challenge> findInProgressChallengeByUserId(Long userId) {
        return challengeJpaRepository.findChallengeByUserIdAndIsInProgress(userId, true);
    }

    @Override
    public List<Challenge> findAllByUserId(Long userId){
        return challengeJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Integer countByProductIdAndIsInProgress(Long productId, boolean isInProgress) {
        return challengeJpaRepository.countByProductIdAndIsInProgress(productId, true);
    }

    @Override
    public Optional<Challenge> findSuccessChallengeByIdAndUser_Id(Long userId, Long challengeId) {
        return challengeJpaRepository.findChallengeByIdAndUser_IdAndAchievementState(userId, challengeId, true);
    }
}
