package com.kangkimleekojangcho.akgimi.user.application;

import com.kangkimleekojangcho.akgimi.user.config.JwtConfigProperties;
import com.kangkimleekojangcho.akgimi.user.domain.User;
import com.kangkimleekojangcho.akgimi.user.domain.UserState;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenIssuer {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    private final JwtConfigProperties jwtConfigProperties;

    public String createAccessTokenForUnauthorizedUser(User user) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("userState", user.getUserState().name());
        claims.put("isSimplePasswordChecked",false);
        return createJwt(claims, "ACCESSTOKEN", jwtConfigProperties.getAccessTokenValidTimeInMillisecondUnit());
    }

    public String createAccessTokenForAuthorizedUser(User user) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("userState", user.getUserState().name());
        claims.put("isSimplePasswordChecked",true);
        return createJwt(claims, "ACCESSTOKEN", jwtConfigProperties.getAccessTokenValidTimeInMillisecondUnit());
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("userState", user.getUserState().name());
        return createJwt(claims, "REFRESHTOKEN", jwtConfigProperties.getAccessTokenValidTimeInMillisecondUnit());
    }

    private String createJwt(Claims claims, String tokenType, Long tokenValidTime) {
        return Jwts.builder()
                .setHeaderParam("type", tokenType)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, jwtConfigProperties.getSecretKey())
                .compact();
    }
}
