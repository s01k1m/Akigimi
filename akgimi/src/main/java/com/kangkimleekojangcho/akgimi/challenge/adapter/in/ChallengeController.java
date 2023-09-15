package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreateChallengeRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.CreateChallengeService;
import com.kangkimleekojangcho.akgimi.challenge.application.request.CreateChallengeServiceRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreateChallengeServiceResponse;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class ChallengeController {
    private final CreateChallengeService createChallengeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateChallengeServiceResponse>> createChallenge(
            HttpServletRequest servletRequest,
            @RequestBody CreateChallengeRequest request){
        Long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        CreateChallengeServiceResponse response = createChallengeService.create(userId, request.toServiceRequest());
        return ResponseFactory.success(response);
    }
}
