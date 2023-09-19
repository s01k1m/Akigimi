package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfFeedWrittenByFollowerRequestService;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FeedController {

    private final CreateFeedService createFeedService;
    private final GetBunchOfFeedWrittenByFollowerRequestService getBunchOfFeedWrittenByFollowerRequestService;
    private final SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;

    @PostMapping("/feed")
    ResponseEntity<SuccessResponse<Long>> createFeed(
            @Valid CreateFeedRequest createFeedRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        Long result = createFeedService.createFeed(createFeedRequest.toServiceRequest(), userId);
        return ResponseFactory.success(result);
    }

    @GetMapping("/feed")
    ResponseEntity<SuccessResponse<GetBunchOfFeedWrittenByFollowerServiceResponse>> createFeed(
            @Valid GetBunchOfFeedWrittenByFollowerRequest getBunchOfFeedWrittenByFollowerRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        return ResponseFactory.success(getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                userId, getBunchOfFeedWrittenByFollowerRequest.toServiceRequest()));
    }
}
