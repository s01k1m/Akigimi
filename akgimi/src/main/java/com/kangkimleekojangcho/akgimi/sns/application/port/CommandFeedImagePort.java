package com.kangkimleekojangcho.akgimi.sns.application.port;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CommandFeedImagePort {
    Optional<String> save(MultipartFile file, long userId);
}
