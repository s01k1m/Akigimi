package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.GetBunchOfFeedWrittenByFollowerRequest;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.MarkLikeToFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import com.kangkimleekojangcho.akgimi.sns.application.GetBunchOfFeedWrittenByFollowerRequestService;
import com.kangkimleekojangcho.akgimi.sns.application.MarkLikeToFeedService;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import com.kangkimleekojangcho.akgimi.sns.application.response.MarkLikeToFeedServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/feeds")
@RestController
public class FeedController {

    private final CreateFeedService createFeedService;
    private final GetBunchOfFeedWrittenByFollowerRequestService getBunchOfFeedWrittenByFollowerRequestService;
    private final SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;
    private final MarkLikeToFeedService markLikeToFeedService;

    @PostMapping
    ResponseEntity<SuccessResponse<Long>> getBunchOfFeed(
            @Valid CreateFeedRequest createFeedRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        Long result = createFeedService.createFeed(createFeedRequest.toServiceRequest(), userId);
        return ResponseFactory.success(result);
    }

    @GetMapping
    ResponseEntity<SuccessResponse<GetBunchOfFeedWrittenByFollowerServiceResponse>> getBunchOfFeed(
            @Valid GetBunchOfFeedWrittenByFollowerRequest getBunchOfFeedWrittenByFollowerRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        return ResponseFactory.success(getBunchOfFeedWrittenByFollowerRequestService.getBunchOfFeed(
                userId, getBunchOfFeedWrittenByFollowerRequest.toServiceRequest()));
    }

    @PostMapping("likes")
    ResponseEntity<SuccessResponse<MarkLikeToFeedServiceResponse>> markLikeToFeed(
            HttpServletRequest servletRequest, @RequestBody @Valid MarkLikeToFeedRequest markLikeToFeedRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        return ResponseFactory.success(markLikeToFeedService.execute(userId, markLikeToFeedRequest.feedId()));
    }
}
