package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreatePostscriptServiceRequest(
        Long challengeId,
        String content,
        MultipartFile photo
) {

}
