package com.kangkimleekojangcho.akgimi.sns.adapter.in;

import com.kangkimleekojangcho.akgimi.global.response.ResponseFactory;
import com.kangkimleekojangcho.akgimi.global.response.SuccessResponse;
import com.kangkimleekojangcho.akgimi.sns.adapter.in.request.CreateFeedRequest;
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

    @PostMapping("/feed")
    public ResponseEntity<SuccessResponse<CreateFeedRequest>> createFeed(@Valid @RequestBody CreateFeedRequest request) {
        log.info(request.akgimPlace());
        return ResponseFactory.success(request);
    }


}
