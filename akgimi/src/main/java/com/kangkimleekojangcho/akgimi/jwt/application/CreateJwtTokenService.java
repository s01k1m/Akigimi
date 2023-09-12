package com.kangkimleekojangcho.akgimi.jwt.application;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.user.config.JwtConfigProperties;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateJwtTokenService {
    private final JwtConfigProperties jwtConfigProperties;

    public JwtToken create(String tokenString) {
        byte[] secretKey = jwtConfigProperties.getSecretKey();
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.TOKEN_EXPIRED);
        }
        return new JwtToken(claimsJws);
    }
}
