package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreateChallengeRequest;
import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.RetryChallengeRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.*;
import com.kangkimleekojangcho.akgimi.challenge.application.response.*;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class ChallengeController {
    private final SubtractUserIdFromAccessTokenService subtractUserIdFromAccessTokenService;
    private final CreateChallengeService createChallengeService;
    private final GetChallengeInProgressService getChallengeInProgressService;
    private final GetAllChallengesService getAllChallengesService;
    private final GetParticipantNumberService getParticipantNumberService;
    private final UpdateChallengeStatusService updateChallengeStatusService;
    private final RetryChallengeService retryChallengeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateChallengeServiceResponse>> createChallenge(
            HttpServletRequest servletRequest,
            @Valid @RequestBody CreateChallengeRequest request){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        CreateChallengeServiceResponse response = createChallengeService.create(userId, request.toServiceRequest());
        return ResponseFactory.success(response);
    }

    @GetMapping("/in-progress")
    public ResponseEntity<SuccessResponse<GetChallengeInProgressServiceResponse>> getChallengeInProgress(
            HttpServletRequest servletRequest){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        GetChallengeInProgressServiceResponse response = getChallengeInProgressService.get(userId);
        return ResponseFactory.success(response);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<GetAllChallengesServiceResponse>>> getAllChallenges(
            HttpServletRequest servletRequest) {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        List<GetAllChallengesServiceResponse> response = getAllChallengesService.getAllChallenges(userId);
        return ResponseFactory.success(response);
    }

    @GetMapping("/participants-number")
    public ResponseEntity<SuccessResponse<GetParticipantNumberServiceResponse>> getChallengeParticipantNumber(
        HttpServletRequest servletRequest,
        @RequestParam(name = "product-id") Long productId
    ){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        GetParticipantNumberServiceResponse response = getParticipantNumberService.get(productId);
        return ResponseFactory.success(response);
    }

    @PutMapping
    public ResponseEntity<SuccessResponse<UpdateChallengeStatusServiceResponse>> updateChallengeStatus(
            HttpServletRequest servletRequest,
            @RequestParam(name="succeed") boolean isSucceed){
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);
        UpdateChallengeStatusServiceResponse response = updateChallengeStatusService.update(userId, isSucceed);
        return ResponseFactory.success(response);
    }

    @PostMapping("/retry")
    public ResponseEntity<SuccessResponse<RetryChallengeServiceResponse>> retryChallenge(
            HttpServletRequest servletRequest,
            @Valid @RequestBody RetryChallengeRequest request)
    {
        Long userId = subtractUserIdFromAccessTokenService.subtract(servletRequest);

        RetryChallengeServiceResponse response = retryChallengeService.create(userId, request.toServiceRequest());
        return ResponseFactory.success(response);
    }

}
