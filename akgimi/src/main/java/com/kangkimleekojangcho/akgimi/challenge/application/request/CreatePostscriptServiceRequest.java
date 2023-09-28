package com.kangkimleekojangcho.akgimi.challenge.application.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreatePostscriptServiceRequest(
        Long challengeId,
        String content,
        MultipartFile photo
) {

}
