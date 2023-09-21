package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.out.ChallengeJpaRepository;
import com.kangkimleekojangcho.akgimi.challenge.domain.Challenge;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestException;
import com.kangkimleekojangcho.akgimi.global.exception.BadRequestExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChallengeTestController {
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final ChallengeJpaRepository challengeJpaRepository;

    @DeleteMapping("/challenges/test")
    public ResponseEntity<SuccessResponse<String>> deleteChallenge(
            HttpServletRequest servletRequest){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        Challenge challenge = challengeJpaRepository.findChallengeByUserIdAndIsInProgress(userId, true).orElseThrow(()-> new BadRequestException(BadRequestExceptionCode.NOT_PARTICIPATE_IN_CHALLENGE));
        challengeJpaRepository.deleteById(challenge.getId());
        return ResponseFactory.success("삭제되었습니다.");
    }
}
