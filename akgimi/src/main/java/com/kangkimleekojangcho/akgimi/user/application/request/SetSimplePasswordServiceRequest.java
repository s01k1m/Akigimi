package com.kangkimleekojangcho.akgimi.user.application.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
public class SetSimplePasswordServiceRequest {
    private final Long userId;
    private final String password;

    public SetSimplePasswordServiceRequest(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
