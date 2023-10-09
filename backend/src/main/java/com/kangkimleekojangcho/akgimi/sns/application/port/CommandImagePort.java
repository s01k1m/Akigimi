package com.kangkimleekojangcho.akgimi.sns.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface CommandImagePort {
    String save(MultipartFile file, long userId);
}
