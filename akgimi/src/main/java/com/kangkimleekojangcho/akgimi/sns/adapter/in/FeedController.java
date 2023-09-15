package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FeedController {

    private final CreateFeedService createFeedService;
    private final SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;

    @PostMapping("/feed")
    ResponseEntity<SuccessResponse<Long>> createFeed(
            @Valid @RequestBody CreateFeedRequest createFeedRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        Long result = createFeedService.createFeed(createFeedRequest.toServiceRequest(), userId);
        return ResponseFactory.success(result);
    }
}
