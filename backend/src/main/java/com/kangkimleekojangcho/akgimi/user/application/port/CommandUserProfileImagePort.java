package com.kangkimleekojangcho.akgimi.user.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface CommandUserProfileImagePort {
    String save(Long userId, MultipartFile multipartFile);
}
