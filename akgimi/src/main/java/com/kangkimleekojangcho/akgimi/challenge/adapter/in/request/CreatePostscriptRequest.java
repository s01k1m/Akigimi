package com.kangkimleekojangcho.akgimi.challenge.adapter.in.request;


import com.kangkimleekojangcho.akgimi.challenge.application.request.CreatePostscriptServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreatePostscriptRequest(
        @NotNull(message = "challenge를 입력해주세요")
        Long challengeId,
        @NotNull(message = "사진을 담아주세요")
        MultipartFile photo,
        @NotBlank(message = "content가 필요해요")
        String content
) {

        public CreatePostscriptServiceRequest toServiceRequest() {
                return CreatePostscriptServiceRequest.builder()
                        .photo(photo)
                        .content(content)
                        .challengeId(challengeId)
                        .build();
        }
}
