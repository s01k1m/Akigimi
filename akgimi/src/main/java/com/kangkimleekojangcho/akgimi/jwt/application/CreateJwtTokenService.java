package com.kangkimleekojangcho.akgimi.jwt.application;

import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateJwtTokenService {
    public JwtToken create(String tokenString) {
        return null;
    }
}
