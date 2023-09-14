package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
import com.kangkimleekojangcho.akgimi.sns.application.CreateFeedService;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
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
    private final
    @PostMapping("/feed")
    public ResponseEntity<SuccessResponse<CreateFeedRequest>> createFeed(
            @Valid @RequestBody CreateFeedRequest createFeedRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        createFeedService.createFeed(createFeedRequest.toServiceRequest());
        return ResponseFactory.success(createFeedRequest);
    }


}
