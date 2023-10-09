package com.kangkimleekojangcho.akgimi.user.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class RefreshToken extends JwtToken {

    public RefreshToken(String rawToken, Jws<Claims> claimsJws) {
        super(rawToken, claimsJws);
    }
}
