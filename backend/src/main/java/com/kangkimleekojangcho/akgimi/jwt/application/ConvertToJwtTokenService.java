package com.kangkimleekojangcho.akgimi.jwt.application;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import com.kangkimleekojangcho.akgimi.user.config.JwtConfigProperties;
import com.kangkimleekojangcho.akgimi.user.domain.AccessToken;
import com.kangkimleekojangcho.akgimi.user.domain.JwtToken;
import com.kangkimleekojangcho.akgimi.user.domain.RefreshToken;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConvertToJwtTokenService {
    private final JwtConfigProperties jwtConfigProperties;

    public AccessToken convertToAccessToken(String rawToken) {
        Jws<Claims> claimsJws = convertToJwtClaim(rawToken);
        if(!getType(claimsJws).equals("ACCESSTOKEN")){
            throw new UnauthorizedException(UnauthorizedExceptionCode.NOT_ACCESS_TOKEN);
        }
        return new AccessToken(rawToken,claimsJws);
    }

    private String getType(Jws<Claims> claimsJws) {
        return (String) claimsJws.getHeader().get("type");
    }

    public RefreshToken convertToRefreshToken(String rawToken) {
        Jws<Claims> claimsJws = convertToJwtClaim(rawToken);
        if(!getType(claimsJws).equals("REFRESHTOKEN")){
            throw new UnauthorizedException(UnauthorizedExceptionCode.NOT_REFRESH_TOKEN);
        }
        return new RefreshToken(rawToken,claimsJws);
    }

    private Jws<Claims> convertToJwtClaim(String rawToken) {
        byte[] secretKey = jwtConfigProperties.getSecretKey();
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(rawToken);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(UnauthorizedExceptionCode.TOKEN_EXPIRED);
        }
    }
}
