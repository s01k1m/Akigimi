package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import com.kangkimleekojangcho.akgimi.challenge.adapter.in.request.CreatePostscriptRequest;
import com.kangkimleekojangcho.akgimi.common.domain.application.SubtractUserIdFromAccessTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postscripts")
public class PostscriptController {

    private final SubtractUserIdFromAccessTokenService userIdFromAccessTokenService;

    @PostMapping
    public void createPostscript(@Valid CreatePostscriptRequest createPostscriptRequest, HttpServletRequest servletRequest) {
        Long userId = userIdFromAccessTokenService.subtract(servletRequest);

    }
}
