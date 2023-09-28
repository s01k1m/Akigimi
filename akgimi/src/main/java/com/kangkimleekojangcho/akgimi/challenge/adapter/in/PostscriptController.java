package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreatePostscriptRequest;
import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.GetBunchOfPostscriptRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.CreatePostscriptService;
import com.kangkimleekojangcho.akgimi.challenge.application.GetBunchOfPostscriptService;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreatePostscriptServiceResponse;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.GetBunchOfFeedWrittenByFollowerRequest;
import com.kangkimleekojangcho.akgimi.sns.application.response.GetBunchOfFeedWrittenByFollowerServiceResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postscripts")
public class PostscriptController {

    private final CreatePostscriptService createPostscriptService;
    private final GetBunchOfPostscriptService getBunchOfPostscriptService;
    private final SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreatePostscriptServiceResponse>> createPostscript(
            @Valid CreatePostscriptRequest createPostscriptRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);

        return ResponseFactory.success(
                createPostscriptService.createPostscript(createPostscriptRequest.toServiceRequest(),userId));
    }

    @GetMapping
    ResponseEntity<SuccessResponse<Void>> getBunchOfFeed(
            @Valid GetBunchOfPostscriptRequest getBunchOfPostscriptRequest,
            HttpServletRequest servletRequest
    ) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);
        return ResponseFactory.success(
                getBunchOfPostscriptService.execute(getBunchOfPostscriptRequest.toServiceRequest(), userId)
        );
    }
}
