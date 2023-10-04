package com.kangkimleekojangcho.akgimi.user.domain;

import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedException;
import com.kangkimleekojangcho.akgimi.global.exception.UnauthorizedExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.Getter;

import java.util.Date;
import java.util.Optional;

@Getter
public class JwtToken {
    private final String rawToken;
    private final Jws<Claims> claimsJws;

    public JwtToken(String rawToken, Jws<Claims> claimsJws) {
        this.rawToken = rawToken;
        this.claimsJws = claimsJws;
    }

    public Optional<String> getValue(String key) {
        Claims body = claimsJws.getBody();
        return Optional.ofNullable(body.get(key).toString());
    }

    public boolean isExpired(Date now) {
        Claims body = claimsJws.getBody();
        return body.getExpiration().before(now);
    }

    public Date getExpiredAt() {
        Claims body = claimsJws.getBody();
        return body.getExpiration();
    }

    public boolean isAccessToken() {
        String type = claimsJws.getHeader().get("type").toString();
        return "ACCESSTOKEN".equals(type);
    }

    public boolean isRefreshToken() {
        String type = claimsJws.getHeader().get("type").toString();
        return "REFRESHTOKEN".equals(type);
    }

    public Long getUserId() throws ExpiredJwtException {
        return Long.parseLong(getValue("id").orElseThrow(() -> new UnauthorizedException(UnauthorizedExceptionCode.INVALID_TOKEN)));
    }

    public UserState getUserState() {
        return UserState.valueOf(getValue("userState").orElseThrow(() -> new UnauthorizedException(UnauthorizedExceptionCode.INVALID_TOKEN)));
    }
}
