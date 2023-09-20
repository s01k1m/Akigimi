package com.kangkimleekojangcho.akgimi.common.domain.application;

import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubtractUserIdFromAccessTokenService {
    public Long subtract(HttpServletRequest servletRequest) {
        Long userId = ((JwtToken) servletRequest.getAttribute("accessToken")).getUserId();
        return userId;
    }
}
