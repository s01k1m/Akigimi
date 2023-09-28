package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreatePostscriptRequest;
import com.kangkimleekojangcho.akgimi.challenge.application.CreatePostscriptService;
import com.kangkimleekojangcho.akgimi.challenge.application.response.CreatePostscriptServiceResponse;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postscripts")
public class PostscriptController {

    private final CreatePostscriptService createPostscriptService;
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
}
