package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreatePostscriptRequest(
        @NotNull
        Long challengeId,
        @NotNull
        MultipartFile multipartFile,
        @NotNull
        String content
) {

}
