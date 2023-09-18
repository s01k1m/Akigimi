package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreateChallengeRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.CreateChallengeService;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreateChallengeServiceResponse;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class ChallengeController {
    private final CreateChallengeService createChallengeService;
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateChallengeServiceResponse>> createChallenge(
            HttpServletRequest servletRequest,
            @Valid @RequestBody CreateChallengeRequest request){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        CreateChallengeServiceResponse response = createChallengeService.create(userId, request.toServiceRequest());
        return ResponseFactory.success(response);
    }
}
